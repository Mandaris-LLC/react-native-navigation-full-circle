
#import "ReactNativeNavigation.h"
#import "RNNStore.h"
#import "RCTBridge.h"

@implementation ReactNativeNavigation

+(void)bootstrap:(NSURL *)jsCodeLocation
{
    [ReactNativeNavigation bootstrap:jsCodeLocation launchOptions:nil];
}

+(void)bootstrap:(NSURL *)jsCodeLocation launchOptions:(NSDictionary *)launchOptions
{
    RNNStore.appDelegate.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
    RNNStore.appDelegate.window.backgroundColor = [UIColor whiteColor];
    
    RCTBridge* bridge = [[RCTBridge alloc] initWithBundleURL:jsCodeLocation moduleProvider:nil launchOptions:launchOptions];
    RNNStore.sharedInstance.bridge = bridge;
}

@end
