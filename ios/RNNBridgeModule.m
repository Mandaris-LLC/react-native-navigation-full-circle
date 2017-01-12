#import "RNNBridgeModule.h"
#import "RNNStore.h"

#import "RNNControllerFactory.h"

@implementation RNNBridgeModule

RCT_EXPORT_MODULE(NativeNavigation);

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(startApp:(NSDictionary*)layout)
{
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        
        RCTBridge* bridge = RNNStore.sharedInstance.bridge;
        
        RNNStore.appDelegate.window.rootViewController = [RNNControllerFactory createRootViewController:layout];
        [RNNStore.appDelegate.window makeKeyAndVisible];
    });
    
    
    
}

@end

