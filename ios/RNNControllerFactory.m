
#import "RNNControllerFactory.h"
#import "RNNLayoutNode.h"
#import "RNNRootViewController.h"

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
	return [[RNNRootViewController alloc]initWithNode:node];
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
