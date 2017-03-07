#import "RNNModalManager.h"

@interface RNNModalManager ()

@property RNNStore *store;

@end

@implementation RNNModalManager


-(instancetype)initWithStore:(RNNStore*)store {
	self = [super init];
	self.store = store;
	return self;
}

-(void)showModal:(UIViewController *)viewController {
	UIViewController *topVC = [self topPresentedVC];
	[topVC presentViewController:viewController animated:YES completion:nil];
}

-(void)dismissModal:(NSString *)containerId {
	[self.store.modalsToDismissArray addObject:containerId];
	[self removePendingNextModalIfOnTop];
}

#pragma mark - private

-(void)removePendingNextModalIfOnTop {
	NSString *containerId = [self.store.modalsToDismissArray lastObject];
	
	UIViewController *modalToDismiss = [self.store findContainerForId:containerId];
	
	if(!modalToDismiss) {
		return;
	}
	
	if (modalToDismiss == [self topPresentedVC]) {
		[modalToDismiss dismissViewControllerAnimated:YES completion:^{
			[self.store.modalsToDismissArray removeObject:containerId];
			[self removePendingNextModalIfOnTop];
		}];
	}
}

-(UIViewController*)topPresentedVC {
	UIViewController *root = UIApplication.sharedApplication.delegate.window.rootViewController;
	while(root.presentedViewController) {
		root = root.presentedViewController;
	}
	return root;
}


@end
