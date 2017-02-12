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
	UIViewController *vc = [factory createLayout:layout];
	
	UIApplication.sharedApplication.delegate.window.rootViewController = vc;
	[UIApplication.sharedApplication.delegate.window makeKeyAndVisible];
}

RCT_EXPORT_METHOD(push:(NSString*)containerId layout:(NSDictionary*)layout)
{
	[self assertReady];
	RNNControllerFactory *factory = [[RNNControllerFactory alloc] initWithRootViewCreator:[RNNReactRootViewCreator new] store:[RNN instance].store];
	UIViewController *newVc = [factory createLayout:layout];
	UIViewController *vc = [[RNN instance].store findContainerForId:containerId];
	
	[[vc navigationController] pushViewController:newVc animated:true];
}

RCT_EXPORT_METHOD(pop:(NSString*)containerId)
{
	[self assertReady];
	UIViewController *vc = [[RNN instance].store findContainerForId:containerId];
	
	[[vc navigationController] popViewControllerAnimated:true];
	[[RNN instance].store removeContainer:containerId];
}

- (void)assertReady
{
	if (![RNN instance].isReadyToReceiveCommands) {
		@throw [NSException exceptionWithName:@"BridgeNotLoadedError" reason:@"Bridge not yet loaded! Send commands after Navigation.events().onAppLaunched() has been called." userInfo:nil];
	}
}

@end

