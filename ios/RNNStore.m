
#import "RNNStore.h"

@interface RNNStore ()

@property NSMapTable *containerStore;

@end

@implementation RNNStore


-(instancetype)init {
	self = [super init];
	self.containerStore = [NSMapTable strongToWeakObjectsMapTable];
	self.modalsToDismissArray = [NSMutableArray new];
	return self;
}


-(UIViewController *)findContainerForId:(NSString *)containerId {
	return [self.containerStore objectForKey:containerId];
	
}


- (void)setContainer:(UIViewController*)viewController containerId:(NSString*)containerId {

	UIViewController *existingVewController = [self findContainerForId:containerId];
	if (existingVewController) {
		@throw [NSException exceptionWithName:@"MultipleContainerId" reason:[@"Container id already exists " stringByAppendingString:containerId] userInfo:nil];
	}
	
	[self.containerStore setObject:viewController forKey:containerId];
}


- (void)removeContainer:(NSString*)containerId {
	[self.containerStore removeObjectForKey:containerId];
}


@end
