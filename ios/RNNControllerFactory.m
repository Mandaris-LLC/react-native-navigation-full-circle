
#import "RNNControllerFactory.h"

#import "RNN.h"
#import "RCTRootView.h"

@implementation RNNControllerFactory

+(UIViewController *)createRootViewController:(NSDictionary *)layout
{
    NSString* containerName = layout[@"container"][@"name"];
    NSString* containerId = layout[@"container"][@"id"];
    
    RCTRootView *reactView = [[RCTRootView alloc] initWithBridge:RNN.instance.bridge moduleName:containerName initialProperties:@{@"containerId": containerId}];
    
    UIViewController* controller = [UIViewController new];
    controller.view = reactView;
    return controller;
}

@end
