
#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface RNNStore : NSObject

-(UIViewController*) findContainerForId:(NSString*)containerId;
-(void) setContainer:(UIViewController*)viewController containerId:(NSString*)containerId;
-(void) removeContainer:(NSString*)containerId;

-(void) setReadyToReceiveCommands:(BOOL)isReady;
-(BOOL) isReadyToReceiveCommands;

-(NSMutableArray*) pendingModalIdsToDismiss;

-(void) clean;

@end
