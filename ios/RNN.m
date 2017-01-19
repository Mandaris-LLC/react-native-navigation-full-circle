
#import "RNN.h"
#import "RNNEventEmitter.h"

#import "RCTBridge.h"

@interface RNN()

@end

@implementation RNN
{
	RCTBridge* bridge;
}

+(instancetype)instance
{
	static RNN *sharedInstance = nil;
	static dispatch_once_t onceToken = 0;
	dispatch_once(&onceToken,^{
		if (sharedInstance == nil)
		{
			sharedInstance = [[RNN alloc] init];
		}
	});
	
	return sharedInstance;
}

-(void)bootstrap:(NSURL *)jsCodeLocation launchOptions:(NSDictionary *)launchOptions
{
	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(onJavaScriptLoaded) name:RCTJavaScriptDidLoadNotification object:nil];
#pragma GCC diagnostic push
#pragma GCC diagnostic ignored "-Wdeprecated-declarations"
	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(onJavaScriptDevReload) name:RCTReloadNotification object:nil];
#pragma GCC diagnostic pop
	// this will load the JS bundle
	bridge = [[RCTBridge alloc] initWithBundleURL:jsCodeLocation moduleProvider:nil launchOptions:launchOptions];
}

-(void)onJavaScriptLoaded
{
	[RNNEventEmitter sendOnAppLaunched];
}

-(void)onJavaScriptDevReload
{
	UIApplication.sharedApplication.delegate.window.rootViewController = nil;
}

-(RCTBridge *)bridge
{
	return bridge;
}

@end
