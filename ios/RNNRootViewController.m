
#import "RNNRootViewController.h"
#import "RCTRootView.h"
#import "RNN.h"

@interface RNNRootViewController()
@end

@implementation RNNRootViewController

-(instancetype)initWithNode:(RNNLayoutNode*)node
{
	self = [super init];
	NSString* containerName = node.data[@"name"];
	
	self.view = [[RCTRootView alloc] initWithBridge:RNN.instance.bridge
													  moduleName:containerName
											   initialProperties:@{@"id": node.nodeId}];
	return self;
}

-(void)viewDidAppear:(BOOL)animated
{
	[super viewDidAppear:animated];
	//send the event onAppear
}

-(void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
	//send the event onDisappear
}

@end
