

#import <Foundation/Foundation.h>

@interface RNNCommandsHandler : NSObject

-(void) setRoot:(NSDictionary*)layout;

-(void) push:(NSString*)containerId layout:(NSDictionary*)layout;

-(void) pop:(NSString*)containerId;

-(void) showModal:(NSDictionary*)layout;

-(void) dismissModal:(NSString*)containerId;

-(void) dismissAllModals;

@end
