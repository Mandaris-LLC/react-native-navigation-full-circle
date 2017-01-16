
#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@class RCTBridge;

@interface RNN : NSObject

+(instancetype)instance;

-(void)bootstrap:(NSURL*)jsCodeLocation launchOptions:(NSDictionary*)launchOptions;

-(RCTBridge*)bridge;

@end
