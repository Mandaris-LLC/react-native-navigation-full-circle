
#import "RNNControllerFactory.h"

#import "RNN.h"
#import "RCTRootView.h"

@interface RNNLayoutNode : NSObject
@property NSString* type;
@property NSString* nodeId;
@property NSDictionary* data;
@property NSArray* children;
@end

@implementation RNNLayoutNode

+(instancetype)create:(NSDictionary *)json
{
	RNNLayoutNode* node = [RNNLayoutNode new];
	node.type = json[@"type"];
	node.nodeId = json[@"id"];
	node.data = json[@"data"];
	node.children = json[@"children"];
	return node;
}

-(BOOL)isContainer
{
	return [self.type isEqualToString:@"Container"];
}

-(BOOL)isContainerStack
{
	return [self.type isEqualToString:@"ContainerStack"];
}

-(BOOL)isTabs
{
	return [self.type isEqualToString:@"Tabs"];
}

-(BOOL)isSideMenuRoot
{
	return [self.type isEqualToString:@"SideMenuRoot"];
}

-(BOOL)isSideMenuLeft
{
	return [self.type isEqualToString:@"SideMenuLeft"];
}

-(BOOL)isSideMenuRight
{
	return [self.type isEqualToString:@"SideMenuRight"];
}

-(BOOL)isSideMenuCenter
{
	return [self.type isEqualToString:@"SideMenuCenter"];
}
@end

@implementation RNNControllerFactory

-(UIViewController *)createRootViewController:(NSDictionary *)layout
{
	return [self fromTree:layout];
}

-(UIViewController*)fromTree:(NSDictionary*)json
{
	RNNLayoutNode* node = [RNNLayoutNode create:json];
	
	if (node.isContainer)
	{
		return [self createContainer:node];
	} else if (node.isContainerStack)
	{
		return [self createContainerStack:node];
	} else if (node.isTabs)
	{
		return [self createTabs:node];
	}
	
	@throw [NSException exceptionWithName:@"UnknownControllerType" reason:[@"Unknown controller type " stringByAppendingString:node.type] userInfo:nil];
}

-(UIViewController*)createContainer:(RNNLayoutNode*)node
{
	NSString* containerName = node.data[@"name"];
	
	RCTRootView *reactView = [[RCTRootView alloc] initWithBridge:RNN.instance.bridge
													  moduleName:containerName
											   initialProperties:@{@"containerId": node.nodeId}];
	
	UIViewController* controller = [UIViewController new];
	controller.view = reactView;
	return controller;
}

-(UINavigationController*)createContainerStack:(RNNLayoutNode*)node
{
	UINavigationController* vc = [[UINavigationController alloc] init];
	
	NSMutableArray* controllers = [NSMutableArray new];
	for (NSDictionary* child in node.children) {
		[controllers addObject:[self fromTree:child]];
	}
	[vc setViewControllers:controllers];
	
	return vc;
}

-(UITabBarController*)createTabs:(RNNLayoutNode*)node
{
	UITabBarController* vc = [[UITabBarController alloc] init];
	
	NSMutableArray* controllers = [NSMutableArray new];
	for (NSDictionary* child in node.children) {
		UIViewController* childVc = [self fromTree:child];
		
		UITabBarItem* item = [[UITabBarItem alloc] initWithTitle:@"A Tab" image:nil tag:1];
		[childVc setTabBarItem:item];
		[controllers addObject:childVc];
	}
	[vc setViewControllers:controllers];
	
	return vc;
}

@end
