
#import "RNNControllerFactory.h"
#import "RNNLayoutNode.h"



@interface RNNControllerFactory ()

@property (nonatomic, strong) id<RNNRootViewCreator> creator;

@end

@implementation RNNControllerFactory

# pragma mark public


-(instancetype)initWithRootViewCreator:(id <RNNRootViewCreator>)creator {
	
	self = [super init];
	self.creator = creator;
	
	return self;
}

-(UIViewController *)createLayout:(NSDictionary *)layout
{
	return [self fromTree:layout];
}

# pragma mark private

-(UIViewController*)fromTree:(NSDictionary*)json
{
	RNNLayoutNode* node = [RNNLayoutNode create:json];
	
	if ( node.isContainer) {
		
		return [self createContainer:node];
	}
	
	else if (node.isContainerStack)	{
		return [self createContainerStack:node];
	}
	
	else if (node.isTabs) {
		return [self createTabs:node];
	}
	
	else if (node.isSideMenuRoot) {
		return [self createSideMenu:node];
	}
	
	else if (node.isSideMenuCenter) {
		return [self createSideMenuChild:node type:RNNSideMenuChildTypeCenter];
	}
	
	else if (node.isSideMenuLeft) {
		return [self createSideMenuChild:node type:RNNSideMenuChildTypeLeft];
	}
	else if (node.isSideMenuRight) {
		return [self createSideMenuChild:node type:RNNSideMenuChildTypeRight];
	}
	
	@throw [NSException exceptionWithName:@"UnknownControllerType" reason:[@"Unknown controller type " stringByAppendingString:node.type] userInfo:nil];
}

-(RNNRootViewController*)createContainer:(RNNLayoutNode*)node
{
	return [[RNNRootViewController alloc] initWithNode:node rootViewCreator:self.creator];
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
	for (NSDictionary *child in node.children) {
		UIViewController* childVc = [self fromTree:child];
		
		UITabBarItem* item = [[UITabBarItem alloc] initWithTitle:@"A Tab" image:nil tag:1];
		[childVc setTabBarItem:item];
		[controllers addObject:childVc];
	}
	[vc setViewControllers:controllers];
	
	return vc;
}

-(UIViewController*)createSideMenu:(RNNLayoutNode*)node
{	
	NSMutableArray* childrenVCs = [NSMutableArray new];
	
	
	for (NSDictionary *child in node.children) {
		UIViewController *vc = [self fromTree:child];
		[childrenVCs addObject:vc];
	}
	
	RNNSideMenuController *sideMenu = [[RNNSideMenuController alloc] initWithControllers:childrenVCs];
	return sideMenu;
}


-(UIViewController*)createSideMenuChild:(RNNLayoutNode*)node type:(RNNSideMenuChildType)type {
	UIViewController* child = [self fromTree:node.children[0]];
	RNNSideMenuChildVC *sideMenuChild = [[RNNSideMenuChildVC alloc] initWithChild: child type:type];
	
	return sideMenuChild;
}




@end
