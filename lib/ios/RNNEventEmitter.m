#import "RNNEventEmitter.h"

@implementation RNNEventEmitter

RCT_EXPORT_MODULE();

static NSString* const onAppLaunched	= @"RNN.appLaunched";
static NSString* const componentDidAppear	= @"RNN.componentDidAppear";
static NSString* const componentDidDisappear	= @"RNN.componentDidDisappear";
static NSString* const onNavigationButtonPressed	= @"RNN.navigationButtonPressed";

-(NSArray<NSString *> *)supportedEvents {
	return @[onAppLaunched, componentDidAppear, componentDidDisappear, onNavigationButtonPressed];
}

# pragma mark public

-(void)sendOnAppLaunched {
	[self send:onAppLaunched body:nil];
}

-(void)sendComponentDidAppear:(NSString *)componentId {
	[self send:componentDidAppear body:componentId];
}

-(void)sendComponentDidDisappear:(NSString *)componentId {
	[self send:componentDidDisappear body:componentId];
}

-(void)sendOnNavigationButtonPressed:(NSString *)componentId buttonId:(NSString*)buttonId {
	[self send:onNavigationButtonPressed body:@{@"componentId":componentId , @"buttonId": buttonId }];
}

# pragma mark private

-(void)send:(NSString *)eventName body:(id)body {
	[self sendEventWithName:eventName body:body];
}

@end
