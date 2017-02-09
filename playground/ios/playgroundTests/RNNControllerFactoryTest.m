//
//  RNNControllerFactoryTest.m
//  playground
//
//  Created by Ran Greenberg on 08/02/2017.
//  Copyright © 2017 Wix. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "RNNControllerFactory.m"

@interface RNNControllerFactoryTest : XCTestCase

@end

@implementation RNNControllerFactoryTest

- (void)setUp {
    [super setUp];
    // Put setup code here. This method is called before the invocation of each test method in the class.
}

- (void)tearDown {
    // Put teardown code here. This method is called after the invocation of each test method in the class.
    [super tearDown];
}

- (void)testCreateContainer_EmptyLayout {
	RNNControllerFactory *factory = [[RNNControllerFactory alloc] init];
	XCTAssertThrows([factory createLayout:@{}]);
}


- (void)testCreateContainer_ContainerLayout {
	
	id <RNNRootViewCreator> creator = nil;
	
	RNNControllerFactory *factory = [[RNNControllerFactory alloc] initWithRootViewCreator:creator];
	id ans = [factory createLayout:@{@"id": @"cntId",
									 @"type": @"Container",
									 @"data": @{},
									 @"children": @[]}];
	XCTAssertTrue([ans isKindOfClass:[UIViewController class]]);
}




@end
