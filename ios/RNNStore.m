
#import "RNNStore.h"

@interface RNNStore ()

@end

@implementation RNNStore {
	NSMapTable* _containerStore;
}

-(instancetype)init {
	self = [super init];
	_containerStore = [NSMapTable strongToWeakObjectsMapTable];
	self.modalsToDismissArray = [NSMutableArray new];
	return self;
}

-(UIViewController *)findContainerForId:(NSString *)containerId {
	return [_containerStore objectForKey:containerId];
}

- (void)setContainer:(UIViewController*)viewController containerId:(NSString*)containerId {
	UIViewController *existingVewController = [self findContainerForId:containerId];
	if (existingVewController) {
		@throw [NSException exceptionWithName:@"MultipleContainerId" reason:[@"Container id already exists " stringByAppendingString:containerId] userInfo:nil];
	}
	
	[_containerStore setObject:viewController forKey:containerId];
}

- (void)removeContainer:(NSString*)containerId {
	[_containerStore removeObjectForKey:containerId];
}


@end
