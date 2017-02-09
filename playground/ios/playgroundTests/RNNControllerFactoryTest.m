//
//  RNNControllerFactoryTest.m
//  playground
//
//  Created by Ran Greenberg on 08/02/2017.
//  Copyright © 2017 Wix. All rights reserved.
//

#import <XCTest/XCTest.h>
#import "RNNControllerFactory.h"


@interface RNNControllerFactoryTest : XCTestCase

@property (nonatomic, strong) id<RNNRootViewCreator> creator;
@property (nonatomic, strong) RNNControllerFactory *factory;

@end

@implementation RNNControllerFactoryTest

- (void)setUp {
	[super setUp];
	self.creator = nil;
	self.factory = [[RNNControllerFactory alloc] initWithRootViewCreator:self.creator];
	
}

- (void)tearDown {
	// Put teardown code here. This method is called after the invocation of each test method in the class.
	[super tearDown];
}

- (void)testCreateLayout_EmptyLayout {
	XCTAssertThrows([self.factory createLayout:@{}]);
}


- (void)testCreateLayout_ContainerLayout {
	
	id ans = [self.factory createLayout:
			  @{@"id": @"cntId",
				@"type": @"Container",
				@"data": @{},
				@"children": @[]}];
	XCTAssertTrue([ans isMemberOfClass:[RNNRootViewController class]]);
}

- (void)testCreateLayout_ContainerStackLayout {
	id ans = [self.factory createLayout:
			  @{@"id": @"cntId",
				@"type": @"ContainerStack",
				@"data": @{},
				@"children": @[]}];
	XCTAssertTrue([ans isMemberOfClass:[UINavigationController class]]);
}

- (void)testCreateLayout_ContainerStackLayoutRecursive {
	UINavigationController* ans = (UINavigationController*) [self.factory createLayout:
															 @{@"id": @"cntId",
															   @"type": @"ContainerStack",
															   @"data": @{},
															   @"children": @[
																	   @{@"id": @"cntId_2",
																		 @"type": @"Container",
																		 @"data": @{},
																		 @"children": @[]}]}];
	
	XCTAssertTrue([ans isMemberOfClass:[UINavigationController class]]);
	XCTAssertTrue(ans.childViewControllers.count == 1);
	XCTAssertTrue([ans.childViewControllers[0] isMemberOfClass:[RNNRootViewController class]]);
}

- (void)testCreateLayout_BottomTabsLayout {
	UITabBarController* tabBar = (UITabBarController*) [self.factory createLayout:
														@{
														  @"id": @"cntId",
														  @"type": @"BottomTabs",
														  @"data": @{},
														  @"children": @[
																  @{@"id": @"cntId_2",
																	@"type": @"ContainerStack",
																	@"data": @{},
																	@"children": @[
																			@{@"id": @"cntId_3",
																			  @"type": @"Container",
																			  @"data": @{},
																			  @"children": @[]}]}]}];
	
	XCTAssertTrue([tabBar isMemberOfClass:[UITabBarController class]]);
	XCTAssertTrue(tabBar.childViewControllers.count == 1);
	XCTAssertTrue([tabBar.childViewControllers[0] isMemberOfClass:[UINavigationController class]]);
	
	UINavigationController *navController = tabBar.childViewControllers[0];
	XCTAssertTrue(navController.childViewControllers.count == 1);
	XCTAssertTrue([navController.childViewControllers[0] isMemberOfClass:[RNNRootViewController class]]);
	
	
}

- (void)testCreateLayout_ContainerSideMenuLayoutCenterOnly {
	RNNSideMenuController *ans = (RNNSideMenuController*) [self.factory createLayout:@{@"id": @"cntId",
																					   @"type": @"SideMenuRoot",
																					   @"data": @{},
																					   @"children": @[@{@"id": @"cntI_2",
																										@"type": @"SideMenuCenter",
																										@"data": @{},
																										@"children": @[
																												@{@"id": @"cntId_3",
																												  @"type": @"Container",
																												  @"data": @{},
																												  @"children": @[]}]}]}];
	XCTAssertTrue([ans isMemberOfClass:[RNNSideMenuController class]]);
	XCTAssertTrue([ans isKindOfClass:[UIViewController class]]);
	XCTAssertTrue([ans.center isMemberOfClass:[RNNSideMenuCenterVC class]]);
	XCTAssertTrue([ans.center.child isMemberOfClass:[RNNRootViewController class]]);
}


- (void)testCreateLayout_ContainerSideMenuLayoutCenterAndLeft {
	RNNSideMenuController *ans = (RNNSideMenuController*) [self.factory createLayout:@{@"id": @"cntId",
																					   @"type": @"SideMenuRoot",
																					   @"data": @{},
																					   @"children": @[
																							   @{@"id": @"cntI_2",
																								 @"type": @"SideMenuCenter",
																								 @"data": @{},
																								 @"children": @[
																										 @{@"id": @"cntId_3",
																										   @"type": @"Container",
																										   @"data": @{},
																										   @"children": @[]}]},
																							   @{@"id": @"cntI_4",
																								 @"type": @"SideMenuLeft",
																								 @"data": @{},
																								 @"children": @[
																										 @{@"id": @"cntId_5",
																										   @"type": @"Container",
																										   @"data": @{},
																										   @"children": @[]}]}]}];
	XCTAssertTrue([ans isMemberOfClass:[RNNSideMenuController class]]);
	XCTAssertTrue([ans isKindOfClass:[UIViewController class]]);
	XCTAssertTrue([ans.center isMemberOfClass:[RNNSideMenuCenterVC class]]);
	XCTAssertTrue([ans.center.child isMemberOfClass:[RNNRootViewController class]]);
	XCTAssertTrue([ans.left isMemberOfClass:[RNNSideMenuLeftVC class]]);
	
	RNNSideMenuLeftVC *left = (RNNSideMenuLeftVC*)ans.left;
	XCTAssertTrue([left.child isMemberOfClass:[RNNRootViewController class]]);
}

- (void)testCreateLayout_ContainerSideMenuLayoutCenterLeftRight {
	RNNSideMenuController *ans = (RNNSideMenuController*) [self.factory createLayout:@{@"id": @"cntId",
																					   @"type": @"SideMenuRoot",
																					   @"data": @{},
																					   @"children": @[
																							   @{@"id": @"cntI_2",
																								 @"type": @"SideMenuCenter",
																								 @"data": @{},
																								 @"children": @[
																										 @{@"id": @"cntId_3",
																										   @"type": @"Container",
																										   @"data": @{},
																										   @"children": @[]}]},
																							   @{@"id": @"cntI_4",
																								 @"type": @"SideMenuLeft",
																								 @"data": @{},
																								 @"children": @[
																										 @{@"id": @"cntId_5",
																										   @"type": @"Container",
																										   @"data": @{},
																										   @"children": @[]}]},
																							   @{@"id": @"cntI_6",
																								 @"type": @"SideMenuRight",
																								 @"data": @{},
																								 @"children": @[
																										 @{@"id": @"cntId_5",
																										   @"type": @"Container",
																										   @"data": @{},
																										   @"children": @[]}]}]}];
	XCTAssertTrue([ans isMemberOfClass:[RNNSideMenuController class]]);
	XCTAssertTrue([ans isKindOfClass:[UIViewController class]]);
	XCTAssertTrue([ans.center isMemberOfClass:[RNNSideMenuCenterVC class]]);
	XCTAssertTrue([ans.center.child isMemberOfClass:[RNNRootViewController class]]);
	XCTAssertTrue([ans.left isMemberOfClass:[RNNSideMenuLeftVC class]]);
	
	RNNSideMenuLeftVC *left = (RNNSideMenuLeftVC*)ans.left;
	XCTAssertTrue([left.child isMemberOfClass:[RNNRootViewController class]]);
	
	XCTAssertTrue([ans.right isMemberOfClass:[RNNSideMenuRightVC class]]);
	RNNSideMenuRightVC *right = (RNNSideMenuRightVC*)ans.right;
	XCTAssertTrue([right.child isMemberOfClass:[RNNRootViewController class]]);
}



@end
