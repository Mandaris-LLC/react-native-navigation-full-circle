
#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>


@interface RNNStore : NSObject

@property NSMutableArray *modalsToDismissArray;
@property BOOL isReadyToReceiveCommands;

- (UIViewController*)findContainerForId:(NSString*)containerId;
- (void)setContainer:(UIViewController*)viewController containerId:(NSString*)containerId;
- (void)removeContainer:(NSString*)containerId;

@end
