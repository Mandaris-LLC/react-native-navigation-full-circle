import * as ContainerRegistry from './containers/ContainerRegistry';

import nativeCommandsSender from './adapters/NativeCommandsSender';
import NativeEventsReceiver from './adapters/NativeEventsReceiver';
import UniqueIdProvider from './adapters/UniqueIdProvider';
import Commands from './commands/Commands';

class Navigation {
  constructor() {
    this.nativeEventsReceiver = new NativeEventsReceiver();
    this.uniqueIdProvider = new UniqueIdProvider();
    this.commands = new Commands(nativeCommandsSender, this.uniqueIdProvider);
  }

  registerContainer(containerName, getContainerFunc) {
    ContainerRegistry.registerContainer(containerName, getContainerFunc);
  }

  startApp(params) {
    this.commands.startApp(params);
  }

  onAppLaunched(fn) {
    this.nativeEventsReceiver.onAppLaunched(fn);
  }
}

const singleton = new Navigation();
module.exports = singleton;
