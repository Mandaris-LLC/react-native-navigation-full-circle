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
    RCTBridge* bridge = RNNStore.sharedInstance.bridge;
    
    RNNStore.appDelegate.window.rootViewController = [RNNControllerFactory createRootViewController:layout];
    [RNNStore.appDelegate.window makeKeyAndVisible];
}

@end

