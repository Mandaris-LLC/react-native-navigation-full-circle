//
//  RNNViewController.m
//  ReactNativeNavigation
//
//  Created by Ran Greenberg on 21/12/2016.
//  Copyright © 2016 artal. All rights reserved.
//

#import "RNNViewController.h"
#import "RCTRootView.h"

#define SCREEN              @"screen"
#define SIDE_MENU           @"sideMenu"
#define TABS                @"tabs"
#define SCREEN_KEY          @"key"


@interface RNNViewController ()
@end


@implementation RNNViewController


#pragma mark - Static function 


+ (UIViewController*)controllerWithLayout:(NSDictionary *)layout bridge:(RCTBridge *)bridge {
    
    UIViewController *controller = nil;
    
    NSDictionary *screen = layout[SCREEN];
    if (screen) {
        NSString *screenKey = screen[SCREEN_KEY];
        if (screenKey)
        
        controller = [RNNViewController navigationControllerWithScreenKey:screenKey bridge:bridge];
    }
    
    return controller;
    
}

+(UINavigationController*)navigationControllerWithScreenKey:(NSString*)screenKey bridge:(RCTBridge*)bridge {
    
    UINavigationController *controller = nil;

    UIViewController *viewController = [UIViewController new];
    RCTRootView *reactView = [[RCTRootView alloc] initWithBridge:bridge moduleName:SCREEN_KEY initialProperties:nil];
    controller = [[UINavigationController alloc] initWithRootViewController:viewController];
    
    return controller;
}


@end

