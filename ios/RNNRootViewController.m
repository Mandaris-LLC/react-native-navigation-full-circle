
#import "RNNRootViewController.h"

@interface RNNRootViewController()
@property NSString* containerId;
@property NSString* containerName;
@property RNNEventEmitter *eventEmitter;
@end

@implementation RNNRootViewController

-(instancetype)initWithNode:(RNNLayoutNode*)node rootViewCreator:(id<RNNRootViewCreator>)creator eventEmitter:(RNNEventEmitter*)eventEmitter {
	self = [super init];
	self.containerId = node.nodeId;
	self.containerName = node.data[@"name"];
	self.eventEmitter = eventEmitter;
	
	self.view = [creator createRootView:self.containerName rootViewId:self.containerId];
	
//	[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(onJsReload) name:RCTJavaScriptWillStartLoadingNotification object:nil];
	
	return self;
}

-(void)viewDidAppear:(BOOL)animated {
	[super viewDidAppear:animated];
	[self.eventEmitter sendContainerStart:self.containerId];
}

-(void)viewDidDisappear:(BOOL)animated {
	[super viewDidDisappear:animated];
	[self.eventEmitter sendContainerStop:self.containerId];
}

///**
// * fix for 
// */
//-(void)onJsReload {
//	[[NSNotificationCenter defaultCenter] removeObserver:self];
//	[[NSNotificationCenter defaultCenter] removeObserver:self.view];
//	self.view = nil;
//}

-(void)dealloc {
	
}

@end
