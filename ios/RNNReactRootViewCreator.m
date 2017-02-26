//
//  RNNReactRootViewCreator.m
//  ReactNativeNavigation
//
//  Created by Ran Greenberg on 08/02/2017.
//  Copyright © 2017 Wix. All rights reserved.
//

#import "RNNReactRootViewCreator.h"
#import "RNN.h"
#import <React/RCTRootView.h>

@implementation RNNReactRootViewCreator

 - (UIView*)createRootView:(NSString*)name rootViewId:(NSString*)rootViewId
{
	
	if (!rootViewId) {
		@throw [NSException exceptionWithName:@"MissingViewId" reason:@"Missing view id" userInfo:nil];
	}
	
	UIView *view = [[RCTRootView alloc] initWithBridge:[RNN instance].bridge
										 moduleName:name
								  initialProperties:@{@"id": rootViewId}];
	
	return view;
}

@end
