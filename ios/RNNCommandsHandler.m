
#import "RNNCommandsHandler.h"


#import "RNN.h"
#import "RNNControllerFactory.h"
#import "RNNReactRootViewCreator.h"
#import "RNNStore.h"
#import "RNNModalManager.h"
#import "RNNNavigationStackManager.h"

@implementation RNNCommandsHandler

#pragma mark - public

-(void) setRoot:(NSDictionary*)layout {
	[self assertReady];
	RNNControllerFactory *factory = [[RNNControllerFactory alloc] initWithRootViewCreator:[RNNReactRootViewCreator new] store:[RNN instance].store];
	UIViewController *vc = [factory createLayoutAndSaveToStore:layout];
	
	UIApplication.sharedApplication.delegate.window.rootViewController = vc;
	[UIApplication.sharedApplication.delegate.window makeKeyAndVisible];
}

-(void) push:(NSString*)containerId layout:(NSDictionary*)layout {
	[self assertReady];
	RNNControllerFactory *factory = [[RNNControllerFactory alloc] initWithRootViewCreator:[RNNReactRootViewCreator new] store:[RNN instance].store];
	UIViewController *newVc = [factory createLayoutAndSaveToStore:layout];
	UIViewController *vc = [[RNN instance].store findContainerForId:containerId];
	
	[[[RNNNavigationStackManager alloc] initWithStore:[RNN instance].store] push:newVc onTop:vc animated:YES];
}

-(void) pop:(NSString*)containerId {
	[self assertReady];
	UIViewController *vc = [[RNN instance].store findContainerForId:containerId];
	[[[RNNNavigationStackManager alloc] initWithStore:[RNN instance].store] pop:vc animated:YES];
	[[RNN instance].store removeContainer:containerId];
}

-(void) showModal:(NSDictionary*)layout {
	[self assertReady];
	RNNControllerFactory *factory = [[RNNControllerFactory alloc] initWithRootViewCreator:[RNNReactRootViewCreator new] store:[RNN instance].store];
	UIViewController *newVc = [factory createLayoutAndSaveToStore:layout];
	[[[RNNModalManager alloc] initWithStore:[RNN instance].store] showModal:newVc];
}

-(void) dismissModal:(NSString*)containerId {
	[self assertReady];
	[[[RNNModalManager alloc] initWithStore:[RNN instance].store] dismissModal:containerId];
}

-(void) dismissAllModals {
	[self assertReady];
	[[[RNNModalManager alloc] initWithStore:[RNN instance].store] dismissAllModals];
}

#pragma mark - private

- (void)assertReady {
	if (![RNN instance].isReadyToReceiveCommands) {
		@throw [NSException exceptionWithName:@"BridgeNotLoadedError" reason:@"Bridge not yet loaded! Send commands after Navigation.events().onAppLaunched() has been called." userInfo:nil];
	}
}

@end
