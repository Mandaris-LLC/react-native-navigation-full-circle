//
//  RNNSideMenuRightVC.h
//  ReactNativeNavigation
//
//  Created by Ran Greenberg on 09/02/2017.
//  Copyright © 2017 Wix. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface RNNSideMenuRightVC : UIViewController

@property (readonly) UIViewController *child;

-(instancetype)initWithChild:(UIViewController*)child;

@end
