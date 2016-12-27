//
//  RNNNavigationController.h
//  ReactNativeNavigation
//
//  Created by Ran Greenberg on 27/12/2016.
//  Copyright © 2016 Wix. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RNNNavigationController : UINavigationController

-(instancetype)initWithRootViewController:(UIViewController*)rooViewController props:(NSDictionary*)props;

@end
