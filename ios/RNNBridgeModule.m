#import "RNNBridgeModule.h"

#import "RNNCommandsHandler.h"

@implementation RNNBridgeModule

RCT_EXPORT_MODULE();

- (dispatch_queue_t)methodQueue {
	return dispatch_get_main_queue();
}

#pragma mark - JS interface

RCT_EXPORT_METHOD(setRoot:(NSDictionary*)layout) {
	[[RNNCommandsHandler new] setRoot:layout];
}

RCT_EXPORT_METHOD(push:(NSString*)containerId layout:(NSDictionary*)layout) {
	[[RNNCommandsHandler new] push:containerId layout:layout];
}

RCT_EXPORT_METHOD(pop:(NSString*)containerId) {
	[[RNNCommandsHandler new] pop:containerId];
}

RCT_EXPORT_METHOD(showModal:(NSDictionary*)layout) {
	[[RNNCommandsHandler new] showModal:layout];
}

RCT_EXPORT_METHOD(dismissModal:(NSString*)containerId) {
	[[RNNCommandsHandler new] dismissModal:containerId];
}

RCT_EXPORT_METHOD(dismissAllModals) {
	[[RNNCommandsHandler new] dismissAllModals];
}

@end

