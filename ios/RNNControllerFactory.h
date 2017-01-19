
#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface RNNControllerFactory : NSObject

-(UIViewController*)createRootViewController:(NSDictionary*)layout;

@end
