import { NativeModules, NativeEventEmitter } from 'react-native';

export default class NativeEventsReceiver {
  constructor() {
    this.emitter = new NativeEventEmitter(NativeModules.RNNEventEmitter);
  }

  onAppLaunched(callback) {
    this.emitter.addListener('RNN_onAppLaunched', callback);
  }
}
