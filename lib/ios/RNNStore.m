
#import "RNNStore.h"

@interface RNNStore ()

@end

@implementation RNNStore {
	NSMapTable* _containerStore;
	NSMutableArray* _pendingModalIdsToDismiss;
	BOOL _isReadyToReceiveCommands;
}

-(instancetype)init {
	self = [super init];
	_isReadyToReceiveCommands = false;
	_containerStore = [NSMapTable strongToWeakObjectsMapTable];
	_pendingModalIdsToDismiss = [NSMutableArray new];
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

-(void)setReadyToReceiveCommands:(BOOL)isReady {
	_isReadyToReceiveCommands = isReady;
}

-(BOOL)isReadyToReceiveCommands {
	return _isReadyToReceiveCommands;
}

-(NSMutableArray *)pendingModalIdsToDismiss {
	return _pendingModalIdsToDismiss;
}

-(void)clean {
	_isReadyToReceiveCommands = false;
	[_pendingModalIdsToDismiss removeAllObjects];
	[_containerStore removeAllObjects];
}

@end
