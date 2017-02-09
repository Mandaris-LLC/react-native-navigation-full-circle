
#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "RNNRootViewCreator.h"
#import "RNNRootViewController.h"
#import "RNNSideMenuController.h"
#import "RNNSideMenuCenterVC.h"
#import "RNNSideMenuLeftVC.h"
#import "RNNSideMenuRightVC.h"

@interface RNNControllerFactory : NSObject

-(instancetype)initWithRootViewCreator:(id <RNNRootViewCreator>)creator;

-(UIViewController*)createLayout:(NSDictionary*)layout;

@end
