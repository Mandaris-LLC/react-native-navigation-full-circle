
#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

#import "RNNEventEmitter.h"
#import <React/RCTBridge.h>
#import "RNNStore.h"

@interface RNN : NSObject

@property (readonly) BOOL isReadyToReceiveCommands;
@property (readonly) RCTBridge* bridge;
@property (readonly) RNNEventEmitter* eventEmitter;
@property (readonly) RNNStore *store;

+(instancetype)instance;

-(void)bootstrap:(NSURL*)jsCodeLocation launchOptions:(NSDictionary*)launchOptions;

@end
