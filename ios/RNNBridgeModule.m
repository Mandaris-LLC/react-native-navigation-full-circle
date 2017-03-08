#import "RNNBridgeModule.h"

@interface RNNBridgeModule ()
@property RNNCommandsHandler* commandsHandler;
@end

@implementation RNNBridgeModule

RCT_EXPORT_MODULE();

- (dispatch_queue_t)methodQueue {
	return dispatch_get_main_queue();
}

-(instancetype)initWithCommandsHandler:(RNNCommandsHandler *)commandsHandler {
	self = [super init];
	self.commandsHandler = commandsHandler;
	return self;
}

#pragma mark - JS interface

RCT_EXPORT_METHOD(setRoot:(NSDictionary*)layout) {
	[self.commandsHandler setRoot:layout];
}

RCT_EXPORT_METHOD(push:(NSString*)containerId layout:(NSDictionary*)layout) {
	[self.commandsHandler push:containerId layout:layout];
}

RCT_EXPORT_METHOD(pop:(NSString*)containerId) {
	[self.commandsHandler pop:containerId];
}

RCT_EXPORT_METHOD(showModal:(NSDictionary*)layout) {
	[self.commandsHandler showModal:layout];
}

RCT_EXPORT_METHOD(dismissModal:(NSString*)containerId) {
	[self.commandsHandler dismissModal:containerId];
}

RCT_EXPORT_METHOD(dismissAllModals) {
	[self.commandsHandler dismissAllModals];
}

@end

