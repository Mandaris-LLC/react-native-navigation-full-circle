
#import <XCTest/XCTest.h>
#import "RNNControllerFactory.h"
#import "RCTRootView.h"

@interface RNNControllerFactoryTest : XCTestCase

@end

@implementation RNNControllerFactoryTest

- (void)setUp {
    [super setUp];
    // Put setup code here. This method is called before the invocation of each test method in the class.
}

- (void)tearDown {
    [super tearDown];
}

- (void)testExample {
  NSDictionary* layout = @{
                           @"container": @{
                               @"name": @"myName",
                               @"id": @"my-id"
                               }
                           };
  UIViewController* rootViewController = [RNNControllerFactory createRootViewController:layout];
  XCTAssert([((RCTRootView*)rootViewController.view).moduleName isEqualToString:@"myName"]);
}

@end
