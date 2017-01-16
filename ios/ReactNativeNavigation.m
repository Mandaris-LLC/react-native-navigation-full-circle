
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
    RNN.appDelegate.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
    RNN.appDelegate.window.backgroundColor = [UIColor whiteColor];
    
    [RNNSplashScreen show];
    
    [RNN.instance init:jsCodeLocation launchOptions:launchOptions];
}

@end
