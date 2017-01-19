import { NativeModules } from 'react-native';

export default class NativeCommandsSender {
  constructor() {
    this.nativeCommandsModule = NativeModules.RNNBridgeModule;
  }

  startApp(layoutTree) {
    this.nativeCommandsModule.startApp(layoutTree);
  }
}
