#import "RNNBridgeModule.h"

#import "RNN.h"
#import "RNNControllerFactory.h"

@implementation RNNBridgeModule

RCT_EXPORT_MODULE();

- (dispatch_queue_t)methodQueue
{
	return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(setRoot:(NSDictionary*)layout)
{
	[self assertReady];
	UIApplication.sharedApplication.delegate.window.rootViewController = [[RNNControllerFactory new] createLayout:layout];
	[UIApplication.sharedApplication.delegate.window makeKeyAndVisible];
}

RCT_EXPORT_METHOD(push:(NSString*)containerId layout:(NSDictionary*)layout)
{
	[self assertReady];
	//TODO implement correctly
	UIViewController* newVc = [[RNNControllerFactory new] createLayout:layout];
	id vc = [UIApplication.sharedApplication.delegate.window.rootViewController childViewControllers][0];
	[[vc navigationController]pushViewController:newVc animated:true];
}

RCT_EXPORT_METHOD(pop:(NSString*)containerId)
{
	[self assertReady];
	//TODO implement correctly
	id vc = [UIApplication.sharedApplication.delegate.window.rootViewController childViewControllers][0];
	[[vc navigationController] popViewControllerAnimated:true];
}

-(void)assertReady
{
	if (![RNN instance].isReadyToReceiveCommands) {
		@throw [NSException exceptionWithName:@"BridgeNotLoadedError" reason:@"Bridge not yet loaded! Send commands after Navigation.events().onAppLaunched() has been called." userInfo:nil];
	}
}

@end

