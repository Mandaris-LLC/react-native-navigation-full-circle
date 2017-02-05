
#import "RNNEventEmitter.h"
#import "RNN.h"

@implementation RNNEventEmitter

RCT_EXPORT_MODULE();

static NSString* const onAppLaunched	= @"RNN.appLaunched";
static NSString* const containerStart	= @"RNN.containerStart";
static NSString* const containerStop	= @"RNN.containerStop";

# pragma mark public

-(void)sendOnAppLaunched
{
	[self send:onAppLaunched body:nil];
}

-(void)sendContainerStart:(NSString *)containerId
{
	[self send:containerStart body:@{@"id": containerId}];
}

-(void)sendContainerStop:(NSString *)containerId
{
	[self send:containerStop body:@{@"id": containerId}];
}

# pragma mark private

-(NSArray<NSString *> *)supportedEvents
{
	return @[onAppLaunched, containerStart, containerStop];
}

-(void)send:(NSString *)eventName body:(id)body
{
	[[RNN.instance.bridge moduleForClass:[RNNEventEmitter class]] sendEventWithName:eventName body:body];
}

@end
