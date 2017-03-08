
#import "RNNReactRootViewCreator.h"
#import "RNN.h"
#import <React/RCTRootView.h>

@implementation RNNReactRootViewCreator {
	RCTBridge *_bridge;
}

-(instancetype)initWithBridge:(RCTBridge*)bridge {
	self = [super init];
	
	_bridge = bridge;
	
	return self;
	
}

- (UIView*)createRootView:(NSString*)name rootViewId:(NSString*)rootViewId {
	if (!rootViewId) {
		@throw [NSException exceptionWithName:@"MissingViewId" reason:@"Missing view id" userInfo:nil];
	}
	
	UIView *view = [[RCTRootView alloc] initWithBridge:_bridge
										 moduleName:name
								  initialProperties:@{@"id": rootViewId}];
	return view;
}

@end
