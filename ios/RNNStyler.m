//
//  RNNStyler.m
//  ReactNativeNavigation
//
//  Created by Ran Greenberg on 24/12/2016.
//  Copyright © 2016 artal. All rights reserved.
//

#import "RNNStyler.h"
#import "RCTConvert.h"


@interface RNNStyler()

@property (nonatomic, readwrite) BOOL _hidesBottomBarWhenPushed;
@property (nonatomic, readwrite) BOOL _statusBarHideWithNavBar;
@property (nonatomic, readwrite) BOOL _statusBarHidden;
@property (nonatomic, readwrite) BOOL _statusBarTextColorSchemeLight;


@end

@implementation RNNStyler


#pragma mark - API Methods


-(void)setStyleOnInit:(UIViewController*)viewController styleParams:(NSDictionary*)styleParams {
    NSNumber *tabBarHidden = styleParams[STYLE_TAB_BAR_HIDDEN];
    self._hidesBottomBarWhenPushed = tabBarHidden ? [tabBarHidden boolValue] : NO;
    
    NSNumber *statusBarHideWithNavBar = styleParams[STYLE_STATUS_BAR_HIDE_WITH_NAV_BAR];
    self._statusBarHideWithNavBar = statusBarHideWithNavBar ? [statusBarHideWithNavBar boolValue] : NO;
    
    NSNumber *statusBarHidden = styleParams[STYLE_STATUS_BAR_HIDDEN];
    self._statusBarHidden = statusBarHidden ? [statusBarHidden boolValue] : NO;
}


-(void)setStyleOnAppear:(UIViewController*)vc styleParams:(NSDictionary*)styleParams {
    
    [RNNStyler setNavBarStyle:vc styleParams:styleParams];
    [self setStyleOnAppear:vc styleParams:styleParams];
    
    NSString *screenBackgroundColor = styleParams[STYLE_SCREEN_BACKGROUD_COLOR];
    if (screenBackgroundColor) {
        vc.view.backgroundColor = [RNNStyler colorForProcessedColor:screenBackgroundColor];
    }
    
    NSString *statusBarTextColorScheme = styleParams[STYLE_STATUS_BAR_TEXT_COLOR_SCHEME];
    if (statusBarTextColorScheme && [statusBarTextColorScheme isEqualToString:STYLE_STATUS_BAR_COLOR_SCHEME_LIGHT]) {
        [RNNStyler navBarForVC:vc].barStyle = UIBarStyleBlack;
        self._statusBarTextColorSchemeLight = YES;
    }
    else {
        [RNNStyler navBarForVC:vc].barStyle = UIBarStyleDefault;
        self._statusBarTextColorSchemeLight = NO;
    }
}


#pragma mark - Setters


+(void)setNavBarStyle:(UIViewController*)vc styleParams:(NSDictionary*)styleParams {
    
    NSString *navBarBackgroundColor = styleParams[STYLE_NAV_BAR_BACKGROUND_COLOR];
    if (navBarBackgroundColor) {
        [RNNStyler navBarForVC:vc].barTintColor = [RNNStyler colorForProcessedColor:navBarBackgroundColor];
    }
    else {
        [RNNStyler navBarForVC:vc].barTintColor = nil;
    }
    
    NSString *navBarTextColor = styleParams[STYLE_NAV_BAR_TEXT_COLOR];
    if (navBarTextColor) {
        [[RNNStyler navBarForVC:vc] setTitleTextAttributes:@{NSForegroundColorAttributeName : [RNNStyler colorForProcessedColor:navBarTextColor]}];
    }
    else {
        [[RNNStyler navBarForVC:vc] setTitleTextAttributes:nil];
    }
    
    NSString *navBarButtonColor = styleParams[STYLE_NAV_BAR_BOTTON_COLOR];
    if (navBarButtonColor) {
        [RNNStyler navBarForVC:vc].tintColor = [RNNStyler colorForProcessedColor:navBarButtonColor];
    }
    else {
        [RNNStyler navBarForVC:vc].tintColor = nil;
    }
    
    BOOL navBarHiddenBool = styleParams[STYLE_NAV_BAR_HIDDEN] ? [styleParams[STYLE_NAV_BAR_HIDDEN] boolValue] : NO;
    if (vc.navigationController.navigationBarHidden != navBarHiddenBool) {
        [vc.navigationController setNavigationBarHidden:navBarHiddenBool animated:YES];
    }
    
    BOOL navBarHideOnScrollBool = styleParams[STYLE_NAV_BAR_HIDE_ON_SCROLL] ? [styleParams[STYLE_NAV_BAR_HIDE_ON_SCROLL] boolValue] : NO;
    if (navBarHideOnScrollBool) {
        vc.navigationController.hidesBarsOnSwipe = YES;
    }
    else {
        vc.navigationController.hidesBarsOnSwipe = NO;
    }
    
    BOOL drawUnderNavBarBool = styleParams[STYLE_DRAW_UNDER_NAV_BAR] ? [styleParams[STYLE_DRAW_UNDER_NAV_BAR] boolValue] : NO;
    if (drawUnderNavBarBool) {
        vc.edgesForExtendedLayout |= UIRectEdgeTop;
    }
    else {
        vc.edgesForExtendedLayout &= ~UIRectEdgeTop;
    }
    
    BOOL navBarBlurBool = styleParams[STYLE_NAV_BAR_BLUR] ? [styleParams[STYLE_NAV_BAR_BLUR] boolValue] : NO;
    BOOL navBarTranslucentBool = styleParams[STYLE_NAV_BAR_TRANSLUCENT] ? [styleParams[STYLE_NAV_BAR_TRANSLUCENT] boolValue] : NO;
    if (navBarTranslucentBool || navBarBlurBool) {
        [RNNStyler navBarForVC:vc].translucent = YES;
    }
    else {
        [RNNStyler navBarForVC:vc].translucent = NO;
    }

}


+(void)setTabBarStyle:(UIViewController*)vc styleParams:(NSDictionary*)styleParams {
    
    NSNumber *drawUnderTabBar = styleParams[STYLE_DRAW_UNDER_TAB_BAR];
    BOOL drawUnderTabBarBool = drawUnderTabBar ? [drawUnderTabBar boolValue] : NO;
    if (drawUnderTabBarBool) {
        vc.edgesForExtendedLayout |= UIRectEdgeBottom;
    }
    else {
        vc.edgesForExtendedLayout &= ~UIRectEdgeBottom;
    }
}


#pragma mark - Helper Static Methods


+(UIColor*)colorForProcessedColor:(NSString*)processedColor {
    return processedColor != (id)[NSNull null] ? [RCTConvert UIColor:processedColor] : nil;
}


+(UINavigationBar*)navBarForVC:(UIViewController*)vc {
    return vc ? vc.navigationController.navigationBar : nil;
}


-(NSDictionary*)storeOriginalNavBarImages:(UIViewController*)vc {
    NSMutableDictionary *originalNavBarImages = [@{} mutableCopy];
    UIImage *bgImage = [[RNNStyler navBarForVC:vc] backgroundImageForBarMetrics:UIBarMetricsDefault];
    if (bgImage != nil) {
        originalNavBarImages[STYLE_NAV_BAR_BACKGROUND_COLOR] = bgImage;
    }
    UIImage *shadowImage = [RNNStyler navBarForVC:vc].shadowImage;
    if (shadowImage != nil) {
        originalNavBarImages[STYLE_NAV_BAR_SHADOW_IMAGE] = shadowImage;
    }
    return originalNavBarImages;
}


@end
