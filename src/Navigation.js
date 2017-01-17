import * as ContainerRegistry from './containers/ContainerRegistry';
import * as Commands from './commands/Commands';

import NativeEventsReceiver from './adapters/NativeEventsReceiver';

class Navigation {
  constructor() {
    this.nativeEventsReceiver = new NativeEventsReceiver();
  }

  registerContainer(containerName, getContainerFunc) {
    ContainerRegistry.registerContainer(containerName, getContainerFunc);
  }

  startApp(params) {
    Commands.startApp(params);
  }

  onAppLaunched(fn) {
    this.nativeEventsReceiver.onAppLaunched(fn);
  }

}

const singleton = new Navigation();

module.exports = singleton;

