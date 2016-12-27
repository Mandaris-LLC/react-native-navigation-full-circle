//
//  RNNNavigationController.m
//  ReactNativeNavigation
//
//  Created by Ran Greenberg on 27/12/2016.
//  Copyright © 2016 Wix. All rights reserved.
//

#import "RNNNavigationController.h"

@interface RNNNavigationController ()

@end

@implementation RNNNavigationController


-(instancetype)initWithRootViewController:(UIViewController*)rooViewController props:(NSDictionary*)props {
    if (!rooViewController) return nil;
    self = [super initWithRootViewController:rooViewController];
    if (!self) return nil;
    return self;
}

@end
