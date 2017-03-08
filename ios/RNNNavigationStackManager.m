#import "RNNNavigationStackManager.h"

@implementation RNNNavigationStackManager {
	RNNStore *_store;
}


-(instancetype)initWithStore:(RNNStore*)store {
	self = [super init];
	_store = store;
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
