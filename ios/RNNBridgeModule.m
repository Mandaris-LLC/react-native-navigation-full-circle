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
	UIApplication.sharedApplication.delegate.window.rootViewController = [[RNNControllerFactory new] createRootViewController:layout];
	[UIApplication.sharedApplication.delegate.window makeKeyAndVisible];
}

RCT_EXPORT_METHOD(push:(NSString*)containerId layout:(NSDictionary*)layout)
{
	[self assertReady];
	//TODO make this not shitty
	UIViewController* newVc = [[RNNControllerFactory new] createRootViewController:layout];
	id vc = [UIApplication.sharedApplication.delegate.window.rootViewController childViewControllers][0];
	[[vc navigationController]pushViewController:newVc animated:true];
}

-(void)assertReady
{
	if (!RNN.instance.isReadyToReceiveCommands) {
		@throw [NSException exceptionWithName:@"BridgeNotLoadedError" reason:@"Bridge not yet loaded! Send commands after Navigation.events().onAppLaunched() has been called." userInfo:nil];
	}
}

@end

