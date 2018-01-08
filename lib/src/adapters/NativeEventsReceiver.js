const { NativeModules, NativeEventEmitter } = require('react-native');

class NativeEventsReceiver {
  constructor() {
    this.emitter = new NativeEventEmitter(NativeModules.RNNEventEmitter);
  }

  componentDidAppear(callback) {
    this.emitter.addListener('RNN.componentDidAppear', callback);
  }

  componentDidDisappear(callback) {
    this.emitter.addListener('RNN.componentDidDisappear', callback);
  }

  appLaunched(callback) {
    this.emitter.addListener('RNN.appLaunched', callback);
  }

  navigationButtonPressed(callback) {
    this.emitter.addListener('RNN.navigationButtonPressed', callback);
  }
}

module.exports = NativeEventsReceiver;
