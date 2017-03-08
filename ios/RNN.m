
#import "RNN.h"
#import <React/RCTBridge.h>

#import "RNNEventEmitter.h"
#import "RNNSplashScreen.h"
#import "RNNBridgeModule.h"
#import "RNNReactRootViewCreator.h"

@interface RNN() <RCTBridgeDelegate>

@property NSURL* jsCodeLocation;
@property RCTBridge* bridge;
@property RNNEventEmitter* eventEmitter;
@property RNNStore *store;

@end

@implementation RNN

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
	self.jsCodeLocation = jsCodeLocation;
	self.eventEmitter = [RNNEventEmitter new];
	
	UIApplication.sharedApplication.delegate.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
	UIApplication.sharedApplication.delegate.window.backgroundColor = [UIColor whiteColor];
	
	[RNNSplashScreen show];
	
	[self registerForJsEvents];
	
	// this will load the JS bundle
	self.bridge = [[RCTBridge alloc] initWithDelegate:self launchOptions:launchOptions];
}

# pragma mark - RCTBridgeDelegate

-(NSURL *)sourceURLForBridge:(RCTBridge *)bridge {
	return self.jsCodeLocation;
}

-(NSArray<id<RCTBridgeModule>> *)extraModulesForBridge:(RCTBridge *)bridge {
	
	RNNControllerFactory *controllerFactory = [[RNNControllerFactory alloc] initWithRootViewCreator:[RNNReactRootViewCreator new] store:self.store];
	RNNCommandsHandler* commandsHandler = [[RNNCommandsHandler alloc]initWithStore:self.store controllerFactory:controllerFactory];
	
	RNNBridgeModule* bridgeModule = [[RNNBridgeModule alloc] initWithCommandsHandler:commandsHandler];
	return @[bridgeModule];
}

# pragma mark - private

-(void)registerForJsEvents {
	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(onJavaScriptLoaded) name:RCTJavaScriptDidLoadNotification object:self.bridge];
	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(onJavaScriptWillLoad) name:RCTJavaScriptWillStartLoadingNotification	object:self.bridge];
}


-(void)onJavaScriptWillLoad {
	self.store = [RNNStore new];
	[self resetRootViewControllerOnlyOnJSDevReload];
}

-(void)onJavaScriptLoaded {
	self.store.isReadyToReceiveCommands = true;
	[self.eventEmitter sendOnAppLaunched];
}

-(void)resetRootViewControllerOnlyOnJSDevReload {
	if(![UIApplication.sharedApplication.delegate.window.rootViewController isKindOfClass:[RNNSplashScreen class]]) {
		UIApplication.sharedApplication.delegate.window.rootViewController = nil;
	}
}


@end
