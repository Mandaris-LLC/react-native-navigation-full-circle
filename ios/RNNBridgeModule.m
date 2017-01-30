#import "RNNBridgeModule.h"

#import "RNN.h"
#import "RNNControllerFactory.h"

@implementation RNNBridgeModule

RCT_EXPORT_MODULE();

- (dispatch_queue_t)methodQueue
{
	return dispatch_get_main_queue();
}

RCT_EXPORT_METHOD(setRoot:(NSDictionary*)layout)
{
	UIApplication.sharedApplication.delegate.window.rootViewController = [[RNNControllerFactory new] createRootViewController:layout];
	[UIApplication.sharedApplication.delegate.window makeKeyAndVisible];
}

@end

