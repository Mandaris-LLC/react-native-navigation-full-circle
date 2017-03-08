
#import "RNNCommandsHandler.h"

#import "RNNModalManager.h"
#import "RNNNavigationStackManager.h"

@interface RNNCommandsHandler ()
@property RNNControllerFactory *controllerFactory;
@property RNNStore *store;
@end

@implementation RNNCommandsHandler

-(instancetype) initWithStore:(RNNStore*)store controllerFactory:(RNNControllerFactory*)controllerFactory {
	self = [super init];
	self.store = store;
	self.controllerFactory = controllerFactory;
	return self;
}

#pragma mark - public

-(void) setRoot:(NSDictionary*)layout {
	[self assertReady];
	UIViewController *vc = [self.controllerFactory createLayoutAndSaveToStore:layout];
	
	UIApplication.sharedApplication.delegate.window.rootViewController = vc;
	[UIApplication.sharedApplication.delegate.window makeKeyAndVisible];
}

-(void) push:(NSString*)containerId layout:(NSDictionary*)layout {
	[self assertReady];
	
	UIViewController *newVc = [self.controllerFactory createLayoutAndSaveToStore:layout];
	
	// find on who to push
	UIViewController *vc = [self.store findContainerForId:containerId];
	
	// do the actual pushing
	[[[RNNNavigationStackManager alloc] initWithStore:self.store] push:newVc onTop:vc animated:YES];
}

-(void) pop:(NSString*)containerId {
	[self assertReady];
	
	// find who to pop
	UIViewController *vc = [self.store findContainerForId:containerId];
	
	// do the popping
	[[[RNNNavigationStackManager alloc] initWithStore:self.store] pop:vc animated:YES];
	[self.store removeContainer:containerId];
}

-(void) showModal:(NSDictionary*)layout {
	[self assertReady];
	
	UIViewController *newVc = [self.controllerFactory createLayoutAndSaveToStore:layout];
	[[[RNNModalManager alloc] initWithStore:self.store] showModal:newVc];
}

-(void) dismissModal:(NSString*)containerId {
	[self assertReady];
	[[[RNNModalManager alloc] initWithStore:self.store] dismissModal:containerId];
}

-(void) dismissAllModals {
	[self assertReady];
	[[[RNNModalManager alloc] initWithStore:self.store] dismissAllModals];
}

#pragma mark - private

-(void) assertReady {
	if (!self.store.isReadyToReceiveCommands) {
		@throw [NSException exceptionWithName:@"BridgeNotLoadedError" reason:@"Bridge not yet loaded! Send commands after Navigation.events().onAppLaunched() has been called." userInfo:nil];
	}
}

@end
