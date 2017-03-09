#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "RNNStore.h"

@interface RNNNavigationStackManager : NSObject

-(instancetype)initWithStore:(RNNStore*)store;

-(void)push:(UIViewController*)newTop onTop:(NSString*)containerId;
-(void)pop:(NSString*)containerId;

@end
