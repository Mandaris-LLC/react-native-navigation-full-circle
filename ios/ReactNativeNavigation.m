
#import "ReactNativeNavigation.h"
#import "RNN.h"

@implementation ReactNativeNavigation

+(void)bootstrap:(NSURL *)jsCodeLocation {
	[ReactNativeNavigation bootstrap:jsCodeLocation launchOptions:nil];
}

+(void)bootstrap:(NSURL *)jsCodeLocation launchOptions:(NSDictionary *)launchOptions {
	[[RNN sharedInstance] bootstrap:jsCodeLocation launchOptions:launchOptions];
}

@end
