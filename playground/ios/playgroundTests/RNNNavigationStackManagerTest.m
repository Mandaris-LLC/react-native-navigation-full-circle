#import <XCTest/XCTest.h>
//#import <objc/runtime.h>
#import "RNNStore.h"
#import "RNNNavigationStackManager.h"

@interface MockUINavigationController : UINavigationController
@property (nonatomic, strong) NSArray* willReturnVCs;
@end

@implementation MockUINavigationController

-(NSArray<UIViewController *> *)popToViewController:(UIViewController *)viewController animated:(BOOL)animated {
	return self.willReturnVCs;
}

@end


@interface RNNNavigationStackManagerTest : XCTestCase

@end

@implementation RNNNavigationStackManagerTest

- (void)setUp {
    [super setUp];
	
}


- (void)testPop {
	RNNStore *store = [RNNStore new];
	RNNNavigationStackManager *uut = [[RNNNavigationStackManager alloc] initWithStore:store];
	
	UINavigationController *nvc = [[UINavigationController alloc] init];
	
	UIViewController *vc1 = [UIViewController new];
	UIViewController *vc2 = [UIViewController new];

	
	NSArray *vcArray = @[vc1, vc2];
	[nvc setViewControllers:vcArray];
	[store setContainer:vc1 containerId:@"vc1"];
	[store setContainer:vc2 containerId:@"vc2"];
	[uut pop:@"vc2"];
	
	XCTAssertNil([store findContainerForId:@"vc2"]);
	XCTAssertNotNil([store findContainerForId:@"vc1"]);
	
}

- (void)testPopTo_singleVc {
	RNNStore *store = [RNNStore new];
	RNNNavigationStackManager *uut = [[RNNNavigationStackManager alloc] initWithStore:store];
	
	UIViewController *vc1 = [UIViewController new];
	UIViewController *vc2 = [UIViewController new];
	UIViewController *vc3 = [UIViewController new];
	MockUINavigationController *nvc = [[MockUINavigationController alloc] initWithRootViewController:vc1];
	[nvc pushViewController:vc2 animated:NO];
	[nvc pushViewController:vc3 animated:NO];
	nvc.willReturnVCs = @[vc2, vc3];
	
	[store setContainer:vc1 containerId:@"vc1"];
	[store setContainer:vc2 containerId:@"vc2"];
	[store setContainer:vc3 containerId:@"vc3"];
	[uut popTo:@"vc1" fromContainerId:@"vc3"];
	
	XCTAssertNil([store findContainerForId:@"vc2"]);
	XCTAssertNotNil([store findContainerForId:@"vc1"]);
	
}


//- (void)testPopTo {
//	RNNStore *store = [RNNStore new];
//	RNNNavigationStackManager *uut = [[RNNNavigationStackManager alloc] initWithStore:store];
//	
//	UINavigationController *nvc = [[UINavigationController alloc] init];
//	
//	UIViewController *vc1 = [UIViewController new];
//	UIViewController *vc2 = [UIViewController new];
//	UIViewController *vc3 = [UIViewController new];
//	UIViewController *vc4 = [UIViewController new];
//	
//	NSArray *vcArray = @[vc1, vc2, vc3, vc4];
//	[nvc setViewControllers:vcArray];
//	[store setContainer:vc1 containerId:@"vc1"];
//	[store setContainer:vc2 containerId:@"vc2"];
//	[store setContainer:vc3 containerId:@"vc3"];
//	[store setContainer:vc4 containerId:@"vc4"];
//	[store setContainer:nvc containerId:@"nvc"];
//	
//	[uut popTo:@"vc1" fromContainerId:@"vc4"];
//	
//	XCTAssertNil([store findContainerForId:@"vc4"]);
//	XCTAssertNil([store findContainerForId:@"vc3"]);
//	XCTAssertNil([store findContainerForId:@"vc2"]);
//	
//}


@end
