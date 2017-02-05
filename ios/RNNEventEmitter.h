
#import <Foundation/Foundation.h>

#import "RCTEventEmitter.h"
#import "RCTBridgeModule.h"

@interface RNNEventEmitter : RCTEventEmitter <RCTBridgeModule>

-(void)sendOnAppLaunched;

-(void)sendContainerStart:(NSString*)containerId;

-(void)sendContainerStop:(NSString*)containerId;

@end
