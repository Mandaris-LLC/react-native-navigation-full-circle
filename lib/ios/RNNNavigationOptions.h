#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface RNNNavigationOptions : NSObject

@property (nonatomic, strong, readonly) NSNumber* topBarBackgroundColor;
@property (nonatomic, strong, readonly) NSNumber* topBarTextColor;
@property (nonatomic, strong, readonly) NSNumber* statusBarHidden;
@property (nonatomic, strong, readonly) NSString* title;
@property (nonatomic, strong, readonly) NSString* setTabBadge;

-(instancetype)init;
-(instancetype)initWithDict:(NSDictionary *)navigationOptions;

-(void)applyOn:(UIViewController*)viewController;
-(void)mergeWith:(NSDictionary*)otherOptions;

@end

