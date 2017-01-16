
#import "RNNEventEmitter.h"
#import "RNN.h"

@implementation RNNEventEmitter

RCT_EXPORT_MODULE();

static NSString* const onAppLaunched = @"onAppLaunched";

-(NSArray<NSString *> *)supportedEvents
{
    return @[onAppLaunched];
}

+(void)sendOnAppLaunched
{
    [RNNEventEmitter send:onAppLaunched body:nil];
}

+(void)send:(NSString *)eventName body:(id)body
{
    [[RNN.instance.bridge moduleForClass:[RNNEventEmitter class]] sendEventWithName:eventName body:body];
}

@end
