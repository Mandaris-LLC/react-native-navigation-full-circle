
#import "RNNControllerFactory.h"

#import "RNN.h"
#import "RCTRootView.h"

@implementation RNNControllerFactory

+(UIViewController *)createRootViewController:(NSDictionary *)layout
{
    NSString* containerName = layout[@"container"][@"name"];
    NSString* containerId = layout[@"container"][@"id"];
    
    return [RNNControllerFactory create:containerName containerId:containerId];
}

+(UIViewController*)create:(NSString*)containerName containerId:(NSString*)containerId
{
    RCTRootView *reactView = [[RCTRootView alloc] initWithBridge:RNN.instance.bridge
                                                      moduleName:containerName
                                               initialProperties:@{@"containerId": containerId}];
    
    UIViewController* controller = [UIViewController new];
    controller.view = reactView;
    return controller;
}

@end
