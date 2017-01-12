
#import "ReactNativeNavigation.h"
#import "RNNStore.h"
#import "RCTBridge.h"
#import "RNNSplashScreen.h"
#import "RCTRootView.h";

@implementation ReactNativeNavigation

+(void)bootstrap:(NSURL *)jsCodeLocation
{
    [ReactNativeNavigation bootstrap:jsCodeLocation launchOptions:nil];
}

+(void)bootstrap:(NSURL *)jsCodeLocation launchOptions:(NSDictionary *)launchOptions
{
    
        RNNStore.appDelegate.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
        RNNStore.appDelegate.window.backgroundColor = [UIColor whiteColor];
        
        [RNNSplashScreen showSplashScreenWhileLoadingJS];
        
        RNNStore.sharedInstance.bridge = [[RCTBridge alloc] initWithBundleURL:jsCodeLocation moduleProvider:nil launchOptions:launchOptions];
        
    
}

@end
