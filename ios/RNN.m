
#import "RNN.h"
#import <React/RCTBridge.h>

#import "RNNEventEmitter.h"
#import "RNNSplashScreen.h"
#import "RNNBridgeModule.h"
#import "RNNReactRootViewCreator.h"

@interface RNN() <RCTBridgeDelegate>

@end

@implementation RNN {
	NSURL *_jsCodeLocation;
	RCTBridge *_bridge;
	RNNStore *_store;
}

+(instancetype) sharedInstance {
	static RNN *instance = nil;
	static dispatch_once_t onceToken = 0;
	dispatch_once(&onceToken,^{
		if (instance == nil)
		{
			instance = [[RNN alloc] init];
		}
	});
	
	return instance;
}

# pragma mark public

-(void)bootstrap:(NSURL *)jsCodeLocation launchOptions:(NSDictionary *)launchOptions {
	_jsCodeLocation = jsCodeLocation;
	
	UIApplication.sharedApplication.delegate.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
	UIApplication.sharedApplication.delegate.window.backgroundColor = [UIColor whiteColor];
	
	[RNNSplashScreen show];
	
	[self registerForJsEvents];
	
	// this will load the JS bundle
	_bridge = [[RCTBridge alloc] initWithDelegate:self launchOptions:launchOptions];
}

# pragma mark - RCTBridgeDelegate

-(NSURL *)sourceURLForBridge:(RCTBridge *)bridge {
	return _jsCodeLocation;
}

-(void)onJavaScriptWillLoad {
	_store = [RNNStore new];
	[self resetRootViewControllerOnlyOnJSDevReload];
}

-(NSArray<id<RCTBridgeModule>> *)extraModulesForBridge:(RCTBridge *)bridge {
	RNNEventEmitter *eventEmitter = [[RNNEventEmitter alloc] init];
	
	RNNControllerFactory *controllerFactory = [[RNNControllerFactory alloc] initWithRootViewCreator:[[RNNReactRootViewCreator alloc]initWithBridge:bridge] store:_store eventEmitter:eventEmitter];
	RNNCommandsHandler *commandsHandler = [[RNNCommandsHandler alloc]initWithStore:_store controllerFactory:controllerFactory];
	
	RNNBridgeModule *bridgeModule = [[RNNBridgeModule alloc] initWithCommandsHandler:commandsHandler];
	
	return @[bridgeModule,eventEmitter];
}

-(void)onJavaScriptLoaded {
	_store.isReadyToReceiveCommands = YES;
	[[_bridge moduleForClass:[RNNEventEmitter class]] sendOnAppLaunched];
}

# pragma mark - private

-(void)registerForJsEvents {
	[[NSNotificationCenter defaultCenter] addObserver:self
											 selector:@selector(onJavaScriptLoaded)
												 name:RCTJavaScriptDidLoadNotification
											   object:nil];
	[[NSNotificationCenter defaultCenter] addObserver:self
											 selector:@selector(onJavaScriptWillLoad)
												 name:RCTJavaScriptWillStartLoadingNotification
											   object:nil];
}

-(void)resetRootViewControllerOnlyOnJSDevReload {
	if(![UIApplication.sharedApplication.delegate.window.rootViewController isKindOfClass:[RNNSplashScreen class]]) {
		UIApplication.sharedApplication.delegate.window.rootViewController = nil;
	}
}


@end
