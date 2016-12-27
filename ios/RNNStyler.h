#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

#define STYLE_TAB_BAR_HIDDEN                                    @"tabBarHidden"
#define STYLE_STATUS_BAR_HIDE_WITH_NAV_BAR                      @"statusBarHideWithNavBar"
#define STYLE_STATUS_BAR_HIDDEN                                 @"statusBarHidden"
#define STYLE_SCREEN_BACKGROUD_COLOR                            @"screenBackgroundColor"
#define STYLE_NAV_BAR_BACKGROUND_COLOR                          @"navBarBackgroundColor"
#define STYLE_NAV_BAR_TEXT_COLOR                                @"navBarTextColor"
#define STYLE_NAV_BAR_BOTTON_COLOR                              @"navBarButtonColor"
#define STYLE_STATUS_BAR_TEXT_COLOR_SCHEME                      @"statusBarTextColorScheme"
#define STYLE_STATUS_BAR_COLOR_SCHEME_LIGHT                     @"light"
#define STYLE_NAV_BAR_HIDDEN                                    @"navBarHidden"
#define STYLE_NAV_BAR_HIDE_ON_SCROLL                            @"navBarHideOnScroll"
#define STYLE_DRAW_UNDER_NAV_BAR                                @"drawUnderNavBar"
#define STYLE_DRAW_UNDER_TAB_BAR                                @"drawUnderTabBar"
#define STYLE_NAV_BAR_TRANSLUCENT                               @"navBarTranslucent"
#define STYLE_NAV_BAR_BLUR                                      @"navBarBlur"

#define STYLE_NAV_BAR_SHADOW_IMAGE                              @"shadowImage"
#define STYLE_NAV_BAR_BACKGROUND_IMAGE                          @"bgImage"
//#define STYLE_                      @""


@interface RNNStyler : NSObject

@property (nonatomic, readonly) BOOL _hidesBottomBarWhenPushed;
@property (nonatomic, readonly) BOOL _statusBarHideWithNavBar;
@property (nonatomic, readonly) BOOL _statusBarHidden;
@property (nonatomic, readonly) BOOL _statusBarTextColorSchemeLight;
@property (nonatomic, readonly) BOOL _navBarHairlineImageView;


-(void)setStyleOnInit:(UIViewController*)viewController styleParams:(NSDictionary*)styleParams;
-(void)setStyleOnAppear:(UIViewController*)vc styleParams:(NSDictionary*)styleParams;

@end
