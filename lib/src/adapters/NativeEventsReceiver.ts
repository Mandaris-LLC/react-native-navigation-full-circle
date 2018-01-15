import { NativeModules, NativeEventEmitter } from 'react-native';

export class NativeEventsReceiver {
  private emitter: NativeEventEmitter;

  constructor() {
    this.emitter = new NativeEventEmitter(NativeModules.RNNEventEmitter);
  }

  registerComponentDidAppear(callback: any) {
    this.emitter.addListener('RNN.componentDidAppear', callback);
  }

  registerComponentDidDisappear(callback: any) {
    this.emitter.addListener('RNN.componentDidDisappear', callback);
  }

  registerAppLaunched(callback: any) {
    this.emitter.addListener('RNN.appLaunched', callback);
  }

  registerNavigationButtonPressed(callback: any) {
    this.emitter.addListener('RNN.navigationButtonPressed', callback);
  }
}
