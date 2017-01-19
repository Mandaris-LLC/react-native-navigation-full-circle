
#import "RNNControllerFactory.h"

#import "RNN.h"
#import "RCTRootView.h"

@implementation RNNControllerFactory

-(UIViewController *)createRootViewController:(NSDictionary *)layout
{
    return [self fromTree:layout];
}

-(UIViewController*)fromTree:(NSDictionary*)node
{
    NSString* nodeType = node[@"type"];
    
    NSString* nodeId = node[@"id"];
    NSArray* children = node[@"children"];
    NSDictionary* data = node[@"data"];
    
    if ([nodeType isEqualToString:@"Container"])
    {
        return [self createContainer:nodeId data:data];
    } else if([nodeType isEqualToString:@"ContainerStack"])
    {
        return [self createContainerStack:nodeId data:data children:children];
    }
    
    @throw @"unknown container type";
}

-(UIViewController*)createContainer:(NSString*)containerId data:(NSDictionary*)data
{
    NSString* containerName = data[@"name"];
                                   
    RCTRootView *reactView = [[RCTRootView alloc] initWithBridge:RNN.instance.bridge
                                                      moduleName:containerName
                                               initialProperties:@{@"containerId": containerId}];
    
    UIViewController* controller = [UIViewController new];
    controller.view = reactView;
    return controller;
}

-(UINavigationController*)createContainerStack:(NSString*)containerId data:(NSDictionary*)data children:(NSArray*)children
{
    UINavigationController* vc = [[UINavigationController alloc] init];
    
    NSMutableArray* controllers = [NSMutableArray new];
    for (NSDictionary* node in children) {
        [controllers addObject:[self fromTree:node]];
    }
    [vc setViewControllers:controllers];
    
    return vc;
}

@end
