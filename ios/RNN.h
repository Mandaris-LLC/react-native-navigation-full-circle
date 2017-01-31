
#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@class RCTBridge;

@interface RNN : NSObject

@property (readonly) BOOL isReadyToReceiveCommands;
@property (readonly) RCTBridge* bridge;

+(instancetype)instance;

-(void)bootstrap:(NSURL*)jsCodeLocation launchOptions:(NSDictionary*)launchOptions;

@end
