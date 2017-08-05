#import "RNNNavigationOptions.h"
#import <React/RCTConvert.h>


@implementation RNNNavigationOptions

-(instancetype)init {
	return [self initWithDict:@{}];
}

-(instancetype)initWithDict:(NSDictionary *)navigationOptions {
	if(self = [super init]) {
		self.topBarBackgroundColor = [navigationOptions objectForKey:@"topBarBackgroundColor"];
		self.statusBarHidden = [navigationOptions objectForKey:@"statusBarHidden"];
		self.title = [navigationOptions objectForKey:@"title"];
		self.topBarTextColor = [navigationOptions objectForKey:@"topBarTextColor"];
		self.setTabBadge = [navigationOptions objectForKey:@"setTabBadge"];
	}
	return self;
}

-(void)mergeWith:(NSDictionary *)otherOptions {
	for (id key in otherOptions) {
		[self setValue:[otherOptions objectForKey:key] forKey:key];
	}
}

-(void)applyOn:(UIViewController*)viewController{
	if (self.topBarBackgroundColor) {
		UIColor* backgroundColor = [RCTConvert UIColor:self.topBarBackgroundColor];
		viewController.navigationController.navigationBar.barTintColor = backgroundColor;
	}
	if (self.title) {
		viewController.navigationItem.title = self.title;
	}
	if (self.topBarTextColor) {
		UIColor* textColor = [RCTConvert UIColor:self.topBarTextColor];
		viewController.navigationController.navigationBar.titleTextAttributes = @{NSForegroundColorAttributeName:textColor};
	}
	if (self.setTabBadge) {
		NSString *badge = [RCTConvert NSString:self.setTabBadge];
		if (viewController.navigationController) {
			viewController.navigationController.tabBarItem.badgeValue = badge;
		}
		else {
			viewController.tabBarItem.badgeValue = badge;
		}
	}
}




@end
