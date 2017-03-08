#import "RNNNavigationStackManager.h"

@interface RNNNavigationStackManager ()

@property RNNStore *store;

@end


@implementation RNNNavigationStackManager


-(instancetype)initWithStore:(RNNStore*)store {
	self = [super init];
	self.store = store;
	return self;
}

-(void)push:(UIViewController*)newTop onTop:(UIViewController*)currentTop animated:(BOOL)animated{
	
	[[currentTop navigationController] pushViewController:newTop animated:animated];
}


-(void)pop:(UIViewController*)vc animated:(BOOL)animated{
	
	if([[vc navigationController] topViewController] == vc ) {
		[[vc navigationController] popViewControllerAnimated:animated];
	}
	else {
		NSMutableArray * vcs = [vc navigationController].viewControllers.mutableCopy;
		[vcs removeObject:vc];
		[[vc navigationController] setViewControllers:vcs animated:animated];
	}
}



@end
