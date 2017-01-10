
#import "RNNStore.h"

@interface RNNStore()

@end

@implementation RNNStore

+(instancetype)sharedInstance
{
    static RNNStore *sharedInstance = nil;
    static dispatch_once_t onceToken = 0;
    dispatch_once(&onceToken,^{
        if (sharedInstance == nil)
        {
            sharedInstance = [[RNNStore alloc] init];
        }
    });
    
    return sharedInstance;
}

+(id<UIApplicationDelegate>)appDelegate {
    return UIApplication.sharedApplication.delegate;
}

@end
