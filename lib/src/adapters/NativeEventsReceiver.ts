import { NativeModules, NativeEventEmitter } from 'react-native';

export class NativeEventsReceiver {
  private emitter;

  constructor() {
    this.emitter = new NativeEventEmitter(NativeModules.RNNEventEmitter);
  }

  registerComponentDidAppear(callback) {
    this.emitter.addListener('RNN.componentDidAppear', callback);
  }

  registerComponentDidDisappear(callback) {
    this.emitter.addListener('RNN.componentDidDisappear', callback);
  }

  registerAppLaunched(callback) {
    this.emitter.addListener('RNN.appLaunched', callback);
  }

  registerNavigationButtonPressed(callback) {
    this.emitter.addListener('RNN.navigationButtonPressed', callback);
  }
}
