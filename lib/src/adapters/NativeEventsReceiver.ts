// tslint:disable:no-console
import { NativeModules, NativeEventEmitter } from 'react-native';
import { EventSubscription } from '../interfaces/EventSubscription';

export class NativeEventsReceiver {
  private emitter;
  constructor() {
    if (NativeModules.RNNEventEmitter) {
      this.emitter = new NativeEventEmitter(NativeModules.RNNEventEmitter);
    } else {
      console.log('Using mock NativeEventEmitter module');
      this.emitter = {
        addListener: () => {
          return {
            remove: () => undefined
          };
        }
      };
    }
  }

  public registerAppLaunchedListener(callback: () => void): EventSubscription {
    return this.emitter.addListener('RNN.appLaunched', callback);
  }

  public registerComponentDidAppearListener(callback: (data) => void): EventSubscription {
    return this.emitter.addListener('RNN.componentDidAppear', callback);
  }

  public registerComponentDidDisappearListener(callback: (data) => void): EventSubscription {
    return this.emitter.addListener('RNN.componentDidDisappear', callback);
  }

  public registerCommandCompletedListener(callback: (data) => void): EventSubscription {
    return this.emitter.addListener('RNN.commandCompleted', callback);
  }

  public registerNativeEventListener(callback: (data) => void): EventSubscription {
    return this.emitter.addListener('RNN.nativeEvent', callback);
  }
}
