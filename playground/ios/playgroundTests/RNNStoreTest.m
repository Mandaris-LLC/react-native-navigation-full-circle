//
//  RNNStoreTest.m
//  playground
//
//  Created by Ran Greenberg on 12/02/2017.
//  Copyright © 2017 Wix. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "RNNStore.m"

@interface RNNStoreTest : XCTestCase

@property (nonatomic, strong) RNNStore *store;

@end

@implementation RNNStoreTest

- (void)setUp {
    [super setUp];
	
	self.store = [RNNStore new];
}


- (void)testFindContainerForId_setAndGetsimpleContainerId {
	NSString *containerId1 = @"cntId1";
	NSString *containerId2 = @"cntId2";
	
	UIViewController *vc1 = [UIViewController new];
	UIViewController *vc2 = [UIViewController new];
	
	[self.store setContainer:vc1 containerId:containerId1];
	[self.store setContainer:vc2 containerId:containerId2];
	
	UIViewController *ans = [self.store findContainerForId:containerId1];
	
	XCTAssertEqualObjects(vc1, ans);
	XCTAssertNotEqualObjects(vc2, ans);
}

- (void)testFindContainerForId_setNilContainerId {
	NSString *containerId1 = nil;
	UIViewController *vc1 = [UIViewController new];
	[self.store setContainer:vc1 containerId:containerId1];
	
	UIViewController *ans = [self.store findContainerForId:containerId1];
	XCTAssertNil(ans);
}

- (void)testFindContainerForId_setDoubleContainerId {
	NSString *containerId1 = @"cntId1";
	
	UIViewController *vc1 = [UIViewController new];
	UIViewController *vc2 = [UIViewController new];
	
	[self.store setContainer:vc1 containerId:containerId1];
	
	UIViewController *ans = [self.store findContainerForId:containerId1];
	XCTAssertEqualObjects(vc1, ans);
	XCTAssertThrows([self.store setContainer:vc2 containerId:containerId1]);
}



@end
