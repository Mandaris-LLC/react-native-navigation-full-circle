//
//  RNNSideMenuController.m
//  ReactNativeNavigation
//
//  Created by Ran Greenberg on 09/02/2017.
//  Copyright © 2017 Wix. All rights reserved.
//

#import "RNNSideMenuController.h"
#import "RNNSideMenuCenterVC.h"

@interface RNNSideMenuController ()

@property (readwrite) RNNSideMenuCenterVC *center;
@property (readwrite) RNNSideMenuLeftVC *left;
@property (readwrite) RNNSideMenuRightVC *right;

@end

@implementation RNNSideMenuController

-(instancetype)initWithControllers:(NSArray*)controllers;
 {
	self = [super init];

	 [self setControllers:controllers];
	 
	return self;
}

-(void)setControllers:(NSArray*)controllers {
	for (id controller in controllers) {
		
		if ([controller isKindOfClass:[RNNSideMenuCenterVC class]]) {
			self.center = controller;
		}
		else if ([controller isKindOfClass:[RNNSideMenuLeftVC class]]){
			self.left = controller;
		}
		else if ([controller isKindOfClass:[RNNSideMenuRightVC class]]) {
			self.right = controller;
		}
		else {
			@throw [NSException exceptionWithName:@"UnknownSideMenuControllerType" reason:[@"Unknown side menu type " stringByAppendingString:[controller description]] userInfo:nil];
		}
	}
}

@end
