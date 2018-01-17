#import "RNNOverlayOptions.h"
#import <React/RCTRootView.h>

@implementation RNNOverlayOptions

- (void)applyOn:(UIViewController *)viewController {
	RCTRootView* rootView = (RCTRootView*)viewController.view;
	rootView.passThroughTouches = !self.interceptTouches;
}

@end
