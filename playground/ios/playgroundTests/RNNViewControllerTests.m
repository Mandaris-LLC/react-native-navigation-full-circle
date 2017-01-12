////
////  RNNViewControllerTests.m
////
////  Created by Ran Greenberg on 26/12/2016.
////  Copyright © 2016 Facebook. All rights reserved.
////
//
//#import <XCTest/XCTest.h>
//#import "RCCManager.h"
//#import "RNNViewController.h"
//#import "MMDrawerController.h"
//
//#define COMMAND_SINGLE_SCREEN_APP               @"singleScreenApp"
//#define COMMAND_TAB_BASED_APP                   @"tabBasedApp"
//#define COMMAND_SINGLE_WITH_LEFT_SIDE_MENU      @"singleWithLeftSideMenu"
//#define COMMAND_SINGLE_WITH_RIGHT_SIDE_MENU     @"singleWithRightSideMenu"
//#define COMMAND_SINGLE_WITH_BOTH_SIDE_MENU      @"singleWithBothMenus"
//#define COMMAND_TAB_BASED_WITH_SIDE_MENT        @"tabBasedWithSideMenu"
//
//
//@interface RNNViewControllerTests : XCTestCase
//@property (nonatomic, strong) RCTBridge *bridge;
//@property (nonatomic, strong) NSDictionary *jsonCommands;
//@end
//
//@implementation RNNViewControllerTests
//
//- (void)setUp {
//  [super setUp];
//  // Put setup code here. This method is called before the invocation of each test method in the class.
//  self.bridge = [[RCCManager sharedInstance] getBridge];
//  self.jsonCommands = [self loadCommandsJsonAsDictionary];
//}
//
//- (void)tearDown {
//  // Put teardown code here. This method is called after the invocation of each test method in the class.
//  self.bridge = nil;
//  self.jsonCommands = nil;
//  [super tearDown];
//}
//
//-(NSDictionary*)loadCommandsJsonAsDictionary {
//  NSString *filePath = [[NSBundle mainBundle] pathForResource:@"commands" ofType:@"json"];
//  NSData *data = [NSData dataWithContentsOfFile:filePath];
//  NSDictionary *json = [NSJSONSerialization JSONObjectWithData:data options:kNilOptions error:nil];
//  return json;
//}
//
//- (void)testWrongSyntax {
//  
//  NSDictionary *layout = @{@"key": @"com.example.FirstTabScreen"};
//  id vc = [RNNViewController controllerWithLayout:layout bridge:self.bridge];
//  XCTAssertNil(vc);
//}
//
//-(void)testSingleScreenApp {
//  id vc = [RNNViewController controllerWithLayout:self.jsonCommands[COMMAND_SINGLE_SCREEN_APP] bridge:self.bridge];
//  XCTAssertTrue([vc isKindOfClass:[UINavigationController class]]);
//}
//
//-(void)testTabBasedApp {
//  id vc = [RNNViewController controllerWithLayout:self.jsonCommands[COMMAND_TAB_BASED_APP] bridge:self.bridge];
//  XCTAssertTrue([vc isKindOfClass:[UITabBarController class]]);
//  UITabBarController *tabsController = (UITabBarController*)vc;
//  for (id tab in tabsController.viewControllers) {
//    XCTAssertTrue([tab isKindOfClass:[UINavigationController class]]);
//  }
//}
//
//-(void)testSingleWithLeftSideMenu {
//  id vc = [RNNViewController controllerWithLayout:self.jsonCommands[COMMAND_SINGLE_WITH_LEFT_SIDE_MENU] bridge:self.bridge];
//  XCTAssertTrue([vc isKindOfClass:[MMDrawerController class]]);
//  MMDrawerController *sideMenuLeft = (MMDrawerController*)vc;
//  XCTAssertNotNil(sideMenuLeft.leftDrawerViewController);
//  XCTAssertNotNil(sideMenuLeft.centerViewController);
//  XCTAssertNil(sideMenuLeft.rightDrawerViewController);
//}
//
//-(void)testSingleWithRightSideMenu {
//  id vc = [RNNViewController controllerWithLayout:self.jsonCommands[COMMAND_SINGLE_WITH_RIGHT_SIDE_MENU] bridge:self.bridge];
//  XCTAssertTrue([vc isKindOfClass:[MMDrawerController class]]);
//  MMDrawerController *sideMenuRight = (MMDrawerController*)vc;
//  XCTAssertNil(sideMenuRight.leftDrawerViewController);
//  XCTAssertNotNil(sideMenuRight.centerViewController);
//  XCTAssertNotNil(sideMenuRight.rightDrawerViewController);
//
//}
//
//-(void)testSingleWithBothSideMenu {
//  id vc = [RNNViewController controllerWithLayout:self.jsonCommands[COMMAND_SINGLE_WITH_BOTH_SIDE_MENU] bridge:self.bridge];
//  XCTAssertTrue([vc isKindOfClass:[MMDrawerController class]]);
//  MMDrawerController *sideMenuBoth = (MMDrawerController*)vc;
//  XCTAssertNotNil(sideMenuBoth.leftDrawerViewController);
//  XCTAssertNotNil(sideMenuBoth.centerViewController);
//  XCTAssertNotNil(sideMenuBoth.rightDrawerViewController);
//}
//
//-(void)testTabBasedWithBothSideMenu {
//  id vc = [RNNViewController controllerWithLayout:self.jsonCommands[COMMAND_TAB_BASED_WITH_SIDE_MENT] bridge:self.bridge];
//  XCTAssertTrue([vc isKindOfClass:[MMDrawerController class]]);
//  MMDrawerController *sideMenuBothWithTabs = (MMDrawerController*)vc;
//  XCTAssertNotNil(sideMenuBothWithTabs.leftDrawerViewController);
//  XCTAssertNotNil(sideMenuBothWithTabs.centerViewController);
//  XCTAssertNotNil(sideMenuBothWithTabs.rightDrawerViewController);
//  XCTAssertTrue([sideMenuBothWithTabs.centerViewController isKindOfClass:[UITabBarController class]]);
//}
////- (void)testPerformanceExample {
////  // This is an example of a performance test case.
////  [self measureBlock:^{
////    // Put the code you want to measure the time of here.
////  }];
////}
//
//@end
