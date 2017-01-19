
#import "ReactNativeNavigation.h"

#import "RNNSplashScreen.h"
#import "RNN.h"

@implementation ReactNativeNavigation

+(void)bootstrap:(NSURL *)jsCodeLocation
{
	[ReactNativeNavigation bootstrap:jsCodeLocation launchOptions:nil];
}

+(void)bootstrap:(NSURL *)jsCodeLocation launchOptions:(NSDictionary *)launchOptions
{
	UIApplication.sharedApplication.delegate.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
	UIApplication.sharedApplication.delegate.window.backgroundColor = [UIColor whiteColor];
	
	[RNNSplashScreen show];
	
	[RNN.instance bootstrap:jsCodeLocation launchOptions:launchOptions];
}

@end
