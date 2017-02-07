
#import "RNNRootViewController.h"
#import "RCTRootView.h"
#import "RNN.h"

@interface RNNRootViewController()
@property NSString* containerId;
@property NSString* containerName;
@end

@implementation RNNRootViewController

-(instancetype)initWithNode:(RNNLayoutNode*)node
{
	self = [super init];
	self.containerId = node.nodeId;
	self.containerName = node.data[@"name"];
	
	self.view = [[RCTRootView alloc] initWithBridge:RNN.instance.bridge
										 moduleName:self.containerName
								  initialProperties:@{@"id": self.containerId}];
	return self;
}

-(void)viewDidAppear:(BOOL)animated
{
	[super viewDidAppear:animated];
	[RNN.instance.eventEmitter sendContainerStart:self.containerId];
}

-(void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
	[RNN.instance.eventEmitter sendContainerStop:self.containerId];
}


@end
