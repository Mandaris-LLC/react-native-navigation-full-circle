//
//  RNNViewController.h
//  ReactNativeNavigation
//
//  Created by Ran Greenberg on 21/12/2016.
//  Copyright © 2016 artal. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RCTBridge.h"


@interface RNNViewController : UIViewController

+ (UIViewController*)controllerWithLayout:(NSDictionary *)layout bridge:(RCTBridge *)bridge;


@end
