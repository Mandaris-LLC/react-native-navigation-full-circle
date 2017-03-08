
#import "RNN.h"
#import <React/RCTBridge.h>

#import "RNNEventEmitter.h"
#import "RNNSplashScreen.h"
#import "RNNBridgeModule.h"
#import "RNNRootViewCreator.h"
#import "RNNReactRootViewCreator.h"

@interface RNN() <RCTBridgeDelegate>

@end

@implementation RNN {
	NSURL* _jsCodeLocation;
	NSDictionary* _launchOptions;
	RNNStore* _store;
	RCTBridge* _bridge;
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
	_launchOptions = launchOptions;
	_store = [RNNStore new];

	[RNNSplashScreen show];
	
	[self registerForJsEvents];
	
	[self createBridgeLoadJsThenInitDependencyGraph];
}

# pragma mark - RCTBridgeDelegate

-(NSURL *)sourceURLForBridge:(RCTBridge *)bridge {
	return _jsCodeLocation;
}

-(NSArray<id<RCTBridgeModule>> *)extraModulesForBridge:(RCTBridge *)bridge {
	RNNEventEmitter *eventEmitter = [[RNNEventEmitter alloc] init];
	
	id<RNNRootViewCreator> rootViewCreator = [[RNNReactRootViewCreator alloc] initWithBridge:bridge];
	RNNControllerFactory *controllerFactory = [[RNNControllerFactory alloc] initWithRootViewCreator:rootViewCreator store:_store eventEmitter:eventEmitter];
	RNNCommandsHandler *commandsHandler = [[RNNCommandsHandler alloc] initWithStore:_store controllerFactory:controllerFactory];
	RNNBridgeModule *bridgeModule = [[RNNBridgeModule alloc] initWithCommandsHandler:commandsHandler];
	
	return @[bridgeModule,eventEmitter];
}

# pragma mark - js events

-(void)onJavaScriptWillLoad {
	[_store clean];
	[self resetRootViewControllerOnlyOnJSDevReload];
}

-(void)onJavaScriptLoaded {
	[_store setReadyToReceiveCommands:true];
	[[_bridge moduleForClass:[RNNEventEmitter class]] sendOnAppLaunched];
}

# pragma mark - private

-(void)createBridgeLoadJsThenInitDependencyGraph {
	_bridge = [[RCTBridge alloc] initWithDelegate:self launchOptions:_launchOptions];
}

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
