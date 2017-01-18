
#import "RNNControllerFactory.h"

#import "RNN.h"
#import "RCTRootView.h"

@implementation RNNControllerFactory

+(UIViewController *)createRootViewController:(NSDictionary *)layout
{
    if ([layout[@"type"] isEqualToString:@"ContainerStack"]) {
        UINavigationController* stack = [[UINavigationController alloc] init];
        
        NSString* containerName = [layout[@"children"] objectAtIndex:0][@"data"][@"name"];
        NSString* containerId = [layout[@"children"] objectAtIndex:0][@"id"];
        
        [stack setViewControllers:@[[RNNControllerFactory create:containerName containerId:containerId]] animated:false];
        
        return stack;
    }
    
//    NSString* containerName = layout[@"container"][@"name"];
//    NSString* containerId = layout[@"container"][@"id"];
    
//    return [RNNControllerFactory create:containerName containerId:containerId];
    return nil;
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
