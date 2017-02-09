
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
		return [self createSideMenuCenter:node];
	}
	
	else if (node.isSideMenuLeft) {
		return [self createSideMenuLeft:node];
	}
	else if (node.isSideMenuRight) {
		return [self createSideMenuRight:node];
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
	//	NSArray *childern = node.children;
	//	id center, left, right;
	
	//	for (NSDictionary *child in childern) {
	//		RNNLayoutNode *childNode = [RNNLayoutNode create:child];
	//		UIViewController *vc = [self fromTree:child];
	//
	//		if ([childNode isSideMenuLeft]) {
	//			left = vc;
	//		}
	//		else if ([childNode isSideMenuCenter]) {
	//			center = vc;
	//		}
	//		else if ([childNode isSideMenuRight]) {
	//			right = [self fromTree:child];
	//		}
	//		else {
	//			@throw [NSException exceptionWithName:@"UnknownSideMenuType" reason:[@"Unknown side menu type for side menu root node " stringByAppendingString:childNode.type] userInfo:nil];
	//		}
	//	}
	
	//	if (!center) {
	//		@throw [NSException exceptionWithName:@"WrongSideMenuDefinition" reason:@"Side menu without center is illigal" userInfo:nil];
	//	}
	//
	//	MMDrawerController *sideMenu = [[MMDrawerController alloc] initWithCenterViewController:center leftDrawerViewController:left rightDrawerViewController:right];
	
	NSMutableArray* childrenVCs = [NSMutableArray new];
	
	
	for (NSDictionary *child in node.children) {
		UIViewController *vc = [self fromTree:child];
		[childrenVCs addObject:vc];
	}
	
	RNNSideMenuController *sideMenu = [[RNNSideMenuController alloc] initWithControllers:childrenVCs];
	return sideMenu;
}


-(UIViewController*)createSideMenuCenter:(RNNLayoutNode*)node {
	UIViewController* child = [self fromTree:node.children[0]];
	RNNSideMenuChildVC *center = [[RNNSideMenuChildVC alloc] initWithChild: child type:RNNSideMenuChildTypeCenter];
	
	return center;
}


-(UIViewController*)createSideMenuLeft:(RNNLayoutNode*)node {
	UIViewController* child = [self fromTree:node.children[0]];
	RNNSideMenuChildVC *left = [[RNNSideMenuChildVC alloc] initWithChild: child type:RNNSideMenuChildTypeLeft];
	
	return left;
}


-(UIViewController*)createSideMenuRight:(RNNLayoutNode*)node {
	UIViewController* child = [self fromTree:node.children[0]];
	RNNSideMenuChildVC *right = [[RNNSideMenuChildVC alloc] initWithChild: child type:RNNSideMenuChildTypeRight];
	
	return right;
}


@end
