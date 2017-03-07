#import "RNNBridgeModule.h"

#import "RNN.h"
#import "RNNControllerFactory.h"
#import "RNNReactRootViewCreator.h"
#import "RNNStore.h"

@implementation RNNBridgeModule

RCT_EXPORT_MODULE();

- (dispatch_queue_t)methodQueue
{
	return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(setRoot:(NSDictionary*)layout)
{
	[self assertReady];
	RNNControllerFactory *factory = [[RNNControllerFactory alloc] initWithRootViewCreator:[RNNReactRootViewCreator new] store:[RNN instance].store];
	UIViewController *vc = [factory createLayoutAndSaveToStore:layout];
	
	UIApplication.sharedApplication.delegate.window.rootViewController = vc;
	[UIApplication.sharedApplication.delegate.window makeKeyAndVisible];
}

RCT_EXPORT_METHOD(push:(NSString*)containerId layout:(NSDictionary*)layout)
{
	[self assertReady];
	RNNControllerFactory *factory = [[RNNControllerFactory alloc] initWithRootViewCreator:[RNNReactRootViewCreator new] store:[RNN instance].store];
	UIViewController *newVc = [factory createLayoutAndSaveToStore:layout];
	UIViewController *vc = [[RNN instance].store findContainerForId:containerId];
	
	[[vc navigationController] pushViewController:newVc animated:true];
}

RCT_EXPORT_METHOD(pop:(NSString*)containerId)
{
	[self assertReady];
	UIViewController *vc = [[RNN instance].store findContainerForId:containerId];
	
	[[vc navigationController] popViewControllerAnimated:YES];
	[[RNN instance].store removeContainer:containerId];
}

RCT_EXPORT_METHOD(showModal:(NSDictionary*)layout)
{
	[self assertReady];
	RNNControllerFactory *factory = [[RNNControllerFactory alloc] initWithRootViewCreator:[RNNReactRootViewCreator new] store:[RNN instance].store];
	UIViewController *newVc = [factory createLayoutAndSaveToStore:layout];
	
	UIViewController *root = [self topPresentedVC];
	[root presentViewController:newVc animated:YES completion:^{
		
	}];
}

RCT_EXPORT_METHOD(dismissModal:(NSString*)containerId)
{
	UIViewController *root = [[RNN instance].store findContainerForId:containerId];
	
	if (root) {
		UIViewController *topVC = [self topPresentedVC];
		
		if (root == topVC) {
			[root dismissViewControllerAnimated:YES completion:nil];
			[[RNN instance].store.modalToDismiss dismissViewControllerAnimated:YES completion:nil];
			[RNN instance].store.modalToDismiss = nil;
			
		}
		else {
			[RNN instance].store.modalToDismiss = root;
		}
	}
}

- (void)assertReady
{
	if (![RNN instance].isReadyToReceiveCommands) {
		@throw [NSException exceptionWithName:@"BridgeNotLoadedError" reason:@"Bridge not yet loaded! Send commands after Navigation.events().onAppLaunched() has been called." userInfo:nil];
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

