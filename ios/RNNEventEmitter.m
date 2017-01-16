
#import "RNNEventEmitter.h"

@implementation RNNEventEmitter

RCT_EXPORT_MODULE();

NSString* const RNNEventOnAppLaunched = @"onAppLaunched";

-(NSArray<NSString *> *)supportedEvents
{
    return @[RNNEventOnAppLaunched];
}

@end
