#import "RNNBridgeModule.h"

#import "RNN.h"
#import "RNNControllerFactory.h"
#import "RNNReactRootViewCreator.h"
#import "RNNStore.h"
#import "RNNModalManager.h"
#import "RNNNavigationStackManager.h"

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

	[[[RNNNavigationStackManager alloc] initWithStore:[RNN instance].store] push:newVc onTop:vc animated:YES];
}

RCT_EXPORT_METHOD(pop:(NSString*)containerId)
{
	[self assertReady];
	UIViewController *vc = [[RNN instance].store findContainerForId:containerId];
	[[[RNNNavigationStackManager alloc] initWithStore:[RNN instance].store] pop:vc animated:YES];
	[[RNN instance].store removeContainer:containerId];
}

RCT_EXPORT_METHOD(showModal:(NSDictionary*)layout)
{
	[self assertReady];
	RNNControllerFactory *factory = [[RNNControllerFactory alloc] initWithRootViewCreator:[RNNReactRootViewCreator new] store:[RNN instance].store];
	UIViewController *newVc = [factory createLayoutAndSaveToStore:layout];
	[[[RNNModalManager alloc] initWithStore:[RNN instance].store] showModal:newVc];
}

RCT_EXPORT_METHOD(dismissModal:(NSString*)containerId)
{
	[self assertReady];
	[[[RNNModalManager alloc] initWithStore:[RNN instance].store] dismissModal:containerId];
}

RCT_EXPORT_METHOD(dismissAllModals)
{
	[self assertReady];
	[[[RNNModalManager alloc] initWithStore:[RNN instance].store] dismissAllModals];
}



- (void)assertReady
{
	if (![RNN instance].isReadyToReceiveCommands) {
		@throw [NSException exceptionWithName:@"BridgeNotLoadedError" reason:@"Bridge not yet loaded! Send commands after Navigation.events().onAppLaunched() has been called." userInfo:nil];
	}
}





@end

