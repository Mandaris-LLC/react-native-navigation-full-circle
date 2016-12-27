#import "RNNViewController.h"
#import "RCTRootView.h"
#import "MMDrawerController.h"
#import "RNNStyler.h"


#define SCREEN                  @"screen"
#define SIDE_MENU               @"sideMenu"
#define TABS                    @"tabs"
#define SCREEN_KEY              @"key"
#define SIDE_MENU_LEFT          @"left"
#define SIDE_MENU_RIGHT         @"right"

typedef enum
{
    SideMenuModeNone        = 0,
    SideMenuModeLeft        = 1 << 0,
    SideMenuModeRight       = 1 << 1,
} SideMenuMode;


@interface RNNViewController ()

@property (nonatomic, strong) RNNStyler *styler;

@end


@implementation RNNViewController


#pragma mark - External methods


+ (UIViewController*)controllerWithLayout:(NSDictionary *)layout bridge:(RCTBridge *)bridge {
    UIViewController *controller = nil;
    SideMenuMode sideMenuMode = [RNNViewController sideMenuModeForLayout:layout];
    UIViewController *centerViewController = [RNNViewController centerControllerWithLayout:layout bridge:bridge];
    
    if (sideMenuMode == SideMenuModeNone) {
        controller = centerViewController;
    }
    else {
        NSDictionary *sideMenuLayout = layout[SIDE_MENU];
        controller = [RNNViewController sideMenuControllerWithSideMenuLayout:sideMenuLayout
                                                                sideMenuMode:sideMenuMode
                                                        centerViewController:centerViewController
                                                                      bridge:bridge];
    }
    return controller;
}


#pragma mark - System Methods


- (BOOL)hidesBottomBarWhenPushed
{
    if (!self.styler._hidesBottomBarWhenPushed) return NO;
    return (self.navigationController.topViewController == self);
}

- (BOOL)prefersStatusBarHidden
{
    if (self.styler._statusBarHidden) {
        return YES;
    }
    if (self.styler._statusBarHideWithNavBar) {
        return self.navigationController.isNavigationBarHidden;
    }
    else {
        return NO;
    }
}


#pragma mark - Helper methods


+(SideMenuMode)sideMenuModeForLayout:(NSDictionary*)layout {
    NSDictionary *sideMenu = layout[SIDE_MENU];
    SideMenuMode mode = SideMenuModeNone;
    if (sideMenu) {
        if (sideMenu[SIDE_MENU_LEFT]) {
            mode |= SideMenuModeLeft;
        }
        if (sideMenu[SIDE_MENU_RIGHT]) {
            mode |= SideMenuModeRight;
        }
    }
    return mode;
}


+(UIViewController*)centerControllerWithLayout:(NSDictionary*)layout bridge:(RCTBridge*)bridge {
    UIViewController *controller = nil;
    
    NSDictionary *tabs = layout[TABS];
    NSDictionary *screen = layout[SCREEN];
    if (tabs) {
        controller = [RNNViewController tabBarControllerWithArray:tabs bridge:bridge];
    }
    else if (screen) {
        NSDictionary *screenLayout = @{SCREEN: screen};
        controller = [RNNViewController controllerWithScreenLayout:screenLayout bridge:bridge];
    }
    
    return controller;
}


+(MMDrawerController*)sideMenuControllerWithSideMenuLayout:(NSDictionary*)sideMenuLayout sideMenuMode:(SideMenuMode)sideMenuMode centerViewController:(UIViewController*)centerVC bridge:(RCTBridge*)bridge {
    UIViewController *leftVC, *rightVC = nil;
    
    if (sideMenuMode & SideMenuModeLeft) {
        NSDictionary *leftLayout = sideMenuLayout[SIDE_MENU_LEFT];
        NSString *leftScreenKey = leftLayout[SCREEN_KEY];
        leftVC = [RNNViewController controllerWithScreenKey:leftScreenKey bridge:bridge];
    }
    if (sideMenuMode & SideMenuModeRight) {
        NSDictionary *rightLayout = sideMenuLayout[SIDE_MENU_RIGHT];
        NSString *rightScreenKey = rightLayout[SCREEN_KEY];
        rightVC = [RNNViewController controllerWithScreenKey:rightScreenKey bridge:bridge];
    }
    
    MMDrawerController *controller = [[MMDrawerController alloc] initWithCenterViewController:centerVC
                                                                     leftDrawerViewController:leftVC
                                                                    rightDrawerViewController:rightVC];
    
    controller.openDrawerGestureModeMask = MMOpenDrawerGestureModeAll;
    controller.closeDrawerGestureModeMask = MMCloseDrawerGestureModeAll;
    
    return controller;
}


+(UITabBarController*)tabBarControllerWithArray:(NSArray*)tabs bridge:(RCTBridge*)bridge {
    UIViewController *controller = nil;
    
    NSMutableArray *tabsVC = [NSMutableArray new];
    for (NSDictionary* tab in tabs) {
        UIViewController *tabVC = [RNNViewController controllerWithScreenLayout:tab bridge:bridge];
        if (tabVC) {
            [tabsVC addObject:tabVC];
        }
    }
    if ([tabsVC count] > 0) {
        UITabBarController *tabController = [[UITabBarController alloc] init];
        tabController.viewControllers = tabsVC;
        controller = tabController;
    }
    return controller;
}


+(UIViewController*)controllerWithScreenLayout:(NSDictionary*)screenLayout bridge:(RCTBridge*)bridge {
    UIViewController *controller = nil;
    NSDictionary *screen = screenLayout[SCREEN];
    NSDictionary *screenKey = screen[SCREEN_KEY];
    if (screen && screenKey) {
        UIViewController *rootVC = [RNNViewController controllerWithScreenKey:screenKey bridge:bridge];
        if (rootVC) {
            controller = [[UINavigationController alloc] initWithRootViewController:rootVC];
            [controller.tabBarItem setTitle:screenKey];
        }
    }
    return controller;
}


+(UIViewController*)controllerWithScreenKey:(NSString*)screenKey bridge:(RCTBridge*)bridge {
    UIViewController *controller = nil;
    if (screenKey) {
        RCTRootView *reactView = [[RCTRootView alloc] initWithBridge:bridge moduleName:screenKey initialProperties:nil];
        if (!reactView) return nil;
        controller = [UIViewController new];
        controller.view = reactView;
    }
    return controller;
}

@end
