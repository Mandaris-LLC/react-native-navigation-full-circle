
#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "RCTBridgeModule.h"

@interface RNNStore : NSObject

@property (atomic, strong) RCTBridge* bridge;

+(instancetype)sharedInstance;

+(id<UIApplicationDelegate>)appDelegate;

@end
