//
//  RNNRootViewControllerTest.m
//  ReactNativeNavigation
//
//  Created by Daniel Zlotin on 07/02/2017.
//  Copyright © 2017 Wix. All rights reserved.
//

#import <XCTest/XCTest.h>

#import "RNNRootViewController.h"

@interface RNNRootViewControllerTest : XCTestCase

@end

@implementation RNNRootViewControllerTest


- (void)sendsEventOnAppear {
	NSDictionary* params = @{};
	RNNRootViewController* uut = [[RNNRootViewController alloc] initWithNode:[RNNLayoutNode create:params]];
	[uut viewDidAppear:true];
}


@end
