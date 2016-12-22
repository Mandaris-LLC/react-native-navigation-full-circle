//
//  RNNViewController.m
//  ReactNativeNavigation
//
//  Created by Ran Greenberg on 21/12/2016.
//  Copyright © 2016 artal. All rights reserved.
//

#import "RNNViewController.h"
#import "RCTRootView.h"
#import "MMDrawerController.h"

#define SCREEN                  @"screen"
#define SIDE_MENU               @"sideMenu"
#define TABS                    @"tabs"
#define SCREEN_KEY              @"key"
#define SIDE_MENU_LEFT          @"left"
#define SIDE_MENU_RIGHT         @"right"


@interface RNNViewController ()
@end


@implementation RNNViewController


#pragma mark - Static function


+ (UIViewController*)controllerWithLayout:(NSDictionary *)layout bridge:(RCTBridge *)bridge {
    
    UIViewController *controller = nil;
    NSDictionary *screen = layout[SCREEN];
    NSArray *tabs = layout[TABS];
    NSDictionary *sideMenu = layout[SIDE_MENU];
    
    if (sideMenu) {
        NSDictionary *centerScreenDictionary;
        if (screen) {
            centerScreenDictionary = @{SCREEN: screen};
        }
        else if (tabs){
            centerScreenDictionary = @{TABS: layout[TABS]};
        }
        else {
            return nil;
        }
        
        UIViewController *centerViewController = [RNNViewController controllerWithLayout:centerScreenDictionary bridge:bridge];
        controller = [RNNViewController sideMenuWithLayout:sideMenu centerViewController:centerViewController bridge:bridge];
        
        return controller;
    }
    
    if (tabs) {
        controller = [RNNViewController tabBarWithTabsArray:tabs bridge:bridge];
        return controller;
    }
    
    if (screen) {
        NSString *screenKey = screen[SCREEN_KEY];
        if (screenKey) {
            controller = [RNNViewController navigationControllerWithScreenKey:screenKey bridge:bridge];
        }
        
        return controller;
    }
    
    return controller;
}


+(UIViewController*)controllerWithScreenKey:(NSString*)screenKey bridge:(RCTBridge *)bridge {
    
    UIViewController *controller = nil;
    
    RCTRootView *reactView = [[RCTRootView alloc] initWithBridge:bridge moduleName:screenKey initialProperties:nil];
    if (!reactView) return nil;
    
    controller = [UIViewController new];
    controller.view = reactView;
    
    return controller;
}


+(UINavigationController*)navigationControllerWithScreenKey:(NSString*)screenKey bridge:(RCTBridge*)bridge {
    
    UINavigationController *controller = nil;
    
    UIViewController *viewController = [RNNViewController controllerWithScreenKey:screenKey bridge:bridge];
    if (!viewController) return nil;
    
    controller = [[UINavigationController alloc] initWithRootViewController:viewController];
    [controller.tabBarItem setTitle:@"tab"];
    
    return controller;
}


+(UIViewController*)sideMenuWithLayout:(NSDictionary*)layout centerViewController:(UIViewController*)centerViewController bridge:(RCTBridge*)bridge {
    
    UIViewController *leftViewController, *rightViewController, *sideMenuViewController = nil;
    
    NSDictionary *left = layout[SIDE_MENU_LEFT];
    if (left) {
        NSString *leftScreenKey = left[SCREEN_KEY];
        leftViewController = [RNNViewController controllerWithScreenKey:leftScreenKey bridge:bridge];
    }
    
    NSDictionary *right = layout[SIDE_MENU_RIGHT];
    if (right) {
        NSString *rightScreenKey = right[SCREEN_KEY];
        rightViewController = [RNNViewController controllerWithScreenKey:rightScreenKey bridge:bridge];
    }
    
    if (rightViewController || leftViewController) {
        sideMenuViewController = [[MMDrawerController alloc] initWithCenterViewController:centerViewController leftDrawerViewController:leftViewController rightDrawerViewController:rightViewController];
        
        // TODO: CHANGE THIS
        ((MMDrawerController*)sideMenuViewController).openDrawerGestureModeMask = MMOpenDrawerGestureModeAll;
        ((MMDrawerController*)sideMenuViewController).closeDrawerGestureModeMask = MMCloseDrawerGestureModeAll;
    }
    
    return sideMenuViewController;
}

+(UITabBarController*)tabBarWithTabsArray:(NSArray*)tabsArray bridge:(RCTBridge*)bridge {
    
    UITabBarController *tabBarController = nil;
    NSMutableArray *tabsViewControllersArray = [[NSMutableArray alloc] init];
    
    for (NSDictionary *tab in tabsArray) {
        UIViewController *tabViewController = [RNNViewController controllerWithLayout:tab bridge:bridge];
        if (tabViewController) {
            [tabsViewControllersArray addObject:tabViewController];
        }
    }
    if ([tabsViewControllersArray count] > 0) {
        tabBarController = [UITabBarController new];
        tabBarController.viewControllers = tabsViewControllersArray;
    }
    
    return tabBarController;
}

-(void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    
}





@end

