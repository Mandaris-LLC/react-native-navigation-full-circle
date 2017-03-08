
#import <Foundation/Foundation.h>

#import <React/RCTEventEmitter.h>
#import <React/RCTBridgeModule.h>

@interface RNNEventEmitter : RCTEventEmitter <RCTBridgeModule>

-(instancetype)initWithBridge:(RCTBridge*)bridge;

-(void)sendOnAppLaunched;

-(void)sendContainerStart:(NSString*)containerId;

-(void)sendContainerStop:(NSString*)containerId;

@end
