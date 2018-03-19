import { NativeModules, NativeEventEmitter } from 'react-native';

export interface EventSubscription {
  remove();
}

export class NativeEventsReceiver {
  private emitter: NativeEventEmitter;
  constructor() {
    this.emitter = new NativeEventEmitter(NativeModules.RNNEventEmitter);
  }

  registerAppLaunched(callback): EventSubscription {
    return this.emitter.addListener('RNN.appLaunched', callback);
  }

  registerComponentDidAppear(callback): EventSubscription {
    return this.emitter.addListener('RNN.componentDidAppear', callback);
  }

  registerComponentDidDisappear(callback): EventSubscription {
    return this.emitter.addListener('RNN.componentDidDisappear', callback);
  }

  registerInteraction(callback): EventSubscription {
    return this.emitter.addListener('RNN.interaction', callback);
  }
}
