
#import "RNNReactRootViewCreator.h"
#import "RNN.h"
#import <React/RCTRootView.h>

@interface RNNReactRootViewCreator ()

@property RCTBridge *bridge;

@end

@implementation RNNReactRootViewCreator

-(instancetype)initWithBridge:(RCTBridge*)bridge {
	self = [super init];
	
	self.bridge = bridge;
	
	return self;
	
}

- (UIView*)createRootView:(NSString*)name rootViewId:(NSString*)rootViewId {
	if (!rootViewId) {
		@throw [NSException exceptionWithName:@"MissingViewId" reason:@"Missing view id" userInfo:nil];
	}
	
	UIView *view = [[RCTRootView alloc] initWithBridge:self.bridge
										 moduleName:name
								  initialProperties:@{@"id": rootViewId}];
	return view;
}

@end
