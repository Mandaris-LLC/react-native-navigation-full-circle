
#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface RNN : NSObject

+(instancetype)sharedInstance;

-(void)bootstrap:(NSURL*)jsCodeLocation launchOptions:(NSDictionary*)launchOptions;

@end
