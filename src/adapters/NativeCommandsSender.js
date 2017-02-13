import { NativeModules } from 'react-native';

export default class NativeCommandsSender {
  constructor() {
    this.nativeCommandsModule = NativeModules.RNNBridgeModule;
  }

  setRoot(layoutTree) {
    this.nativeCommandsModule.setRoot(layoutTree);
    return Promise.resolve(layoutTree);
  }

  push(onContainerId, layout) {
    this.nativeCommandsModule.push(onContainerId, layout);
    return Promise.resolve(layout);
  }

  pop(containerId) {
    this.nativeCommandsModule.pop(containerId);
    return Promise.resolve(containerId);
  }

  showModal(layout) {
    this.nativeCommandsModule.showModal(layout);
    return Promise.resolve(layout);
  }

  dismissModal(id) {
    this.nativeCommandsModule.dismissModal(id);
    return Promise.resolve(id);
  }
}

