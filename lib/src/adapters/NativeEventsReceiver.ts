import { NativeModules, NativeEventEmitter, EmitterSubscription } from 'react-native';

export class NativeEventsReceiver {
  private emitter: NativeEventEmitter;
  constructor() {
    this.emitter = new NativeEventEmitter(NativeModules.RNNEventEmitter);
  }

  registerAppLaunched(callback): EmitterSubscription {
    return this.emitter.addListener('RNN.appLaunched', callback);
  }

  registerComponentDidAppear(callback): EmitterSubscription {
    return this.emitter.addListener('RNN.componentDidAppear', callback);
  }

  registerComponentDidDisappear(callback): EmitterSubscription {
    return this.emitter.addListener('RNN.componentDidDisappear', callback);
  }

  registerInteraction(callback): EmitterSubscription {
    return this.emitter.addListener('RNN.interaction', callback);
  }
}
