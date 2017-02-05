
#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

#import "RNNEventEmitter.h"
#import "RCTBridge.h"

@interface RNN : NSObject

@property (readonly) BOOL isReadyToReceiveCommands;
@property (readonly) RCTBridge* bridge;
@property (readonly) RNNEventEmitter* eventEmitter;

+(instancetype)instance;

-(void)bootstrap:(NSURL*)jsCodeLocation launchOptions:(NSDictionary*)launchOptions;

@end
