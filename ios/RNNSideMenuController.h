//
//  RNNSideMenuController.h
//  ReactNativeNavigation
//
//  Created by Ran Greenberg on 09/02/2017.
//  Copyright © 2017 Wix. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RNNSideMenuCenterVC.h"
#import "RNNSideMenuLeftVC.h"
#import "RNNSideMenuRightVC.h"

@interface RNNSideMenuController : UIViewController

@property (readonly) RNNSideMenuCenterVC *center;
@property (readonly) RNNSideMenuLeftVC *left;
@property (readonly) RNNSideMenuRightVC *right;

-(instancetype)initWithControllers:(NSArray*)controllers;

@end
