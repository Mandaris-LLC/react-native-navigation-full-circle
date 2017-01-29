
#import "RNNEventEmitter.h"
#import "RNN.h"

@implementation RNNEventEmitter

RCT_EXPORT_MODULE();

static NSString* const onAppLaunched = @"RNN_onAppLaunched";

-(NSArray<NSString *> *)supportedEvents
{
	return @[onAppLaunched];
}

-(void)sendOnAppLaunched
{
	[self send:onAppLaunched body:nil];
}

-(void)send:(NSString *)eventName body:(id)body
{
	[[RNN.instance.bridge moduleForClass:[RNNEventEmitter class]] sendEventWithName:eventName body:body];
}

@end
