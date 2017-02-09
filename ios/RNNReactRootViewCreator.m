//
//  RNNReactRootViewCreator.m
//  ReactNativeNavigation
//
//  Created by Ran Greenberg on 08/02/2017.
//  Copyright © 2017 Wix. All rights reserved.
//

#import "RNNReactRootViewCreator.h"
#import "RNN.h"
#import "RCTRootView.h"

@implementation RNNReactRootViewCreator

 - (UIView*)createRootView:(NSString*)name rootViewId:(NSString*)rootViewId
{
	
	UIView *view = [[RCTRootView alloc] initWithBridge:[RNN instance].bridge
										 moduleName:name
								  initialProperties:@{@"id": rootViewId}];
	
	return view;
}

@end
