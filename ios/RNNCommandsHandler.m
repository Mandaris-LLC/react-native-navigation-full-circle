
#import "RNNCommandsHandler.h"

#import "RNNModalManager.h"
#import "RNNNavigationStackManager.h"

@implementation RNNCommandsHandler {
	RNNControllerFactory *_controllerFactory;
	RNNStore *_store;
}

-(instancetype) initWithStore:(RNNStore*)store controllerFactory:(RNNControllerFactory*)controllerFactory {
	self = [super init];
	_store = store;
	_controllerFactory = controllerFactory;
	return self;
}

#pragma mark - public

-(void) setRoot:(NSDictionary*)layout {
	[self assertReady];
	UIViewController *vc = [_controllerFactory createLayoutAndSaveToStore:layout];
	
	UIApplication.sharedApplication.delegate.window.rootViewController = vc;
	[UIApplication.sharedApplication.delegate.window makeKeyAndVisible];
}

-(void) push:(NSString*)containerId layout:(NSDictionary*)layout {
	[self assertReady];
	
	UIViewController *newVc = [_controllerFactory createLayoutAndSaveToStore:layout];
	
	// find on who to push
	UIViewController *vc = [_store findContainerForId:containerId];
	
	// do the actual pushing
	[[[RNNNavigationStackManager alloc] initWithStore:_store] push:newVc onTop:vc animated:YES];
}

-(void) pop:(NSString*)containerId {
	[self assertReady];
	
	// find who to pop
	UIViewController *vc = [_store findContainerForId:containerId];
	
	// do the popping
	[[[RNNNavigationStackManager alloc] initWithStore:_store] pop:vc animated:YES];
	[_store removeContainer:containerId];
}

-(void) showModal:(NSDictionary*)layout {
	[self assertReady];
	
	UIViewController *newVc = [_controllerFactory createLayoutAndSaveToStore:layout];
	[[[RNNModalManager alloc] initWithStore:_store] showModal:newVc];
}

-(void) dismissModal:(NSString*)containerId {
	[self assertReady];
	[[[RNNModalManager alloc] initWithStore:_store] dismissModal:containerId];
}

-(void) dismissAllModals {
	[self assertReady];
	[[[RNNModalManager alloc] initWithStore:_store] dismissAllModals];
}

#pragma mark - private

-(void) assertReady {
	if (!_store.isReadyToReceiveCommands) {
		@throw [NSException exceptionWithName:@"BridgeNotLoadedError" reason:@"Bridge not yet loaded! Send commands after Navigation.events().onAppLaunched() has been called." userInfo:nil];
	}
}

@end
