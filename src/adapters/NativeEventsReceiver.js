import { NativeModules, NativeEventEmitter } from 'react-native';

export default class NativeEventsReceiver {
  constructor() {
    this.emitter = new NativeEventEmitter(NativeModules.RNNEventEmitter);
  }

  containerStart(callback) {
    this.emitter.addListener('RNN.containerStart', callback);
  }

  appLaunched(callback) {
    this.emitter.addListener('RNN.appLaunched', callback);
  }
}
