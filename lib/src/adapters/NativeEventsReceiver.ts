import { NativeModules, NativeEventEmitter } from 'react-native';

export interface EventSubscription {
  remove();
}

export class NativeEventsReceiver {
  private emitter: NativeEventEmitter;
  constructor() {
    this.emitter = new NativeEventEmitter(NativeModules.RNNEventEmitter);
  }

  registerAppLaunched(callback: () => void): EventSubscription {
    return this.emitter.addListener('RNN.appLaunched', callback);
  }

  registerComponentDidAppear(callback: (componendId: string, componentName: string) => void): EventSubscription {
    return this.emitter.addListener('RNN.componentDidAppear', callback);
  }

  registerComponentDidDisappear(callback: (componendId: string, componentName: string) => void): EventSubscription {
    return this.emitter.addListener('RNN.componentDidDisappear', callback);
  }

  registerInteraction(callback: (name: string) => void): EventSubscription {
    return this.emitter.addListener('RNN.interaction', callback);
  }
}
