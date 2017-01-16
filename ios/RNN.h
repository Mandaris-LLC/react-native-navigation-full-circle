
#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@class RCTBridge;

@interface RNN : NSObject

+(id<UIApplicationDelegate>)appDelegate;

+(instancetype)instance;

-(void)init:(NSURL*)jsCodeLocation launchOptions:(NSDictionary*)launchOptions;

-(RCTBridge*)bridge;

@end
