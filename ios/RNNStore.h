//
//  RNNStore.h
//  ReactNativeNavigation
//
//  Created by Ran Greenberg on 12/02/2017.
//  Copyright © 2017 Wix. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>


@interface RNNStore : NSObject

@property UIViewController *modalToDismiss;

- (UIViewController*)findContainerForId:(NSString*)containerId;
- (void)setContainer:(UIViewController*)viewController containerId:(NSString*)containerId;
- (void)removeContainer:(NSString*)containerId;

@end
