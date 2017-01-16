
#import <Foundation/Foundation.h>

#import "RCTEventEmitter.h"
#import "RCTBridgeModule.h"

extern NSString* const RNNEventOnAppLaunched;

@interface RNNEventEmitter : RCTEventEmitter <RCTBridgeModule>

@end
