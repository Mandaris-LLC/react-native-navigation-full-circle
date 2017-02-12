//
//  RNNStore.m
//  ReactNativeNavigation
//
//  Created by Ran Greenberg on 12/02/2017.
//  Copyright © 2017 Wix. All rights reserved.
//

#import "RNNStore.h"

@interface RNNStore ()

@property NSMutableDictionary *containerStore;

@end

@implementation RNNStore


-(instancetype)init {
	self = [super init];
	self.containerStore = [NSMutableDictionary new];
	
	return self;
}


-(UIViewController *)findContainerForId:(NSString *)containerId {
	return [self.containerStore valueForKey:containerId];
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
