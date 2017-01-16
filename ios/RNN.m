
#import "RNN.h"
#import "RNNEventEmitter.h"

#import "RCTBridge.h"

@interface RNN()

@end

@implementation RNN {
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

+(id<UIApplicationDelegate>)appDelegate
{
    return UIApplication.sharedApplication.delegate;
}

-(void)init:(NSURL *)jsCodeLocation launchOptions:(NSDictionary *)launchOptions
{
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(onBridgeFinishedLoading) name:RCTJavaScriptDidLoadNotification object:nil];
    // this will load the JS bundle
    bridge = [[RCTBridge alloc] initWithBundleURL:jsCodeLocation moduleProvider:nil launchOptions:launchOptions];
}

-(void)onBridgeFinishedLoading
{
    [[bridge moduleForClass:[RNNEventEmitter class]] sendEventWithName:RNNEventOnAppLaunched body:nil];
}

-(RCTBridge *)bridge
{
    return bridge;
}

@end
