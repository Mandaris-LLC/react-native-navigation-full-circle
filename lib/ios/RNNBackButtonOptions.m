#import "RNNBackButtonOptions.h"

@implementation RNNBackButtonOptions

- (void)applyOn:(UIViewController *)viewController {
	UIImage *image = self.image ? [RCTConvert UIImage:self.image] : nil;
	[viewController.navigationController.navigationBar setBackIndicatorImage:image];
	[viewController.navigationController.navigationBar setBackIndicatorTransitionMaskImage:image];
	
	if ([self.hideTitle boolValue]) {
		self.title = @"";
	}
	
	if (self.title) {
		UIBarButtonItem *backItem = [[UIBarButtonItem alloc] initWithTitle:self.title
																	 style:UIBarButtonItemStylePlain
																	target:nil
																	action:nil];
		
		viewController.navigationItem.backBarButtonItem = backItem;
	}
	
	viewController.navigationItem.hidesBackButton = ![self.visible boolValue];
}

@end
