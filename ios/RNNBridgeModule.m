#import "RNNBridgeModule.h"
#import "RNNStore.h"
#import "RCTRootView.h"

@implementation RNNBridgeModule

RCT_EXPORT_MODULE(NativeNavigation);

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(startApp:(NSDictionary*)layout)
{
    RCTRootView *reactView = [[RCTRootView alloc] initWithBridge:RNNStore.sharedInstance.bridge moduleName:@"com.example.WelcomeScreen" initialProperties:@[@{@"containerId": @"123"}]];
    
    UIViewController* controller = [UIViewController new];
    controller.view = reactView;

    RNNStore.appDelegate.window.rootViewController = controller;
    [RNNStore.appDelegate.window makeKeyAndVisible];
}

@end
