
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
	RNNEventEmitter *_eventEmitter;
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
	_eventEmitter = [RNNEventEmitter new];
	
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

-(NSArray<id<RCTBridgeModule>> *)extraModulesForBridge:(RCTBridge *)bridge {
	
	RNNControllerFactory *controllerFactory = [[RNNControllerFactory alloc] initWithRootViewCreator:[RNNReactRootViewCreator new] store:_store];
	RNNCommandsHandler *commandsHandler = [[RNNCommandsHandler alloc]initWithStore:_store controllerFactory:controllerFactory];
	
	RNNBridgeModule *bridgeModule = [[RNNBridgeModule alloc] initWithCommandsHandler:commandsHandler];
	RNNEventEmitter *eventEmitter = [[RNNEventEmitter alloc] initWithBridge:bridge];
	
	return @[bridgeModule,eventEmitter];
}

# pragma mark - private

-(void)registerForJsEvents {
	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(onJavaScriptLoaded) name:RCTJavaScriptDidLoadNotification object:_bridge];
	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(onJavaScriptWillLoad) name:RCTJavaScriptWillStartLoadingNotification	object:_bridge];
}


-(void)onJavaScriptWillLoad {
	_store = [RNNStore new];
	[self resetRootViewControllerOnlyOnJSDevReload];
}

-(void)onJavaScriptLoaded {
	_store.isReadyToReceiveCommands = true;
	[_eventEmitter sendOnAppLaunched];
}

-(void)resetRootViewControllerOnlyOnJSDevReload {
	if(![UIApplication.sharedApplication.delegate.window.rootViewController isKindOfClass:[RNNSplashScreen class]]) {
		UIApplication.sharedApplication.delegate.window.rootViewController = nil;
	}
}


@end
