#import <XCTest/XCTest.h>
#import "RNNModalManager.m"

@interface RNNModalManagerTest : XCTestCase

@end

@implementation RNNModalManagerTest

- (void)testDismissAllModalsCleansPendingModalsToDismiss {
	RNNStore *store = [RNNStore new];
	[store.modalsToDismissArray addObject:@"modal_id_1"];
	[store.modalsToDismissArray addObject:@"modal_id_2"];
	[store.modalsToDismissArray addObject:@"modal_id_3"];
	
	RNNModalManager *modalManager = [[RNNModalManager alloc] initWithStore:store];
	[modalManager dismissAllModals];
	
	XCTAssertTrue([store.modalsToDismissArray count] == 0);
	
}

@end
