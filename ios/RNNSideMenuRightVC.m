//
//  RNNSideMenuRightVC.m
//  ReactNativeNavigation
//
//  Created by Ran Greenberg on 09/02/2017.
//  Copyright © 2017 Wix. All rights reserved.
//

#import "RNNSideMenuRightVC.h"

@interface RNNSideMenuRightVC ()

@property (readwrite) UIViewController *child;

@end

@implementation RNNSideMenuRightVC


-(instancetype)initWithChild:(UIViewController*)child {
	self = [super init];
	self.child = child;
	return self;
}

@end
