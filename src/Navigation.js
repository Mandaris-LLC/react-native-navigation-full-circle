import NativeCommandsSender from './adapters/NativeCommandsSender';
import NativeEventsReceiver from './adapters/NativeEventsReceiver';
import UniqueIdProvider from './adapters/UniqueIdProvider';

import Store from './containers/Store';
import ContainerRegistry from './containers/ContainerRegistry';
import AppCommands from './commands/AppCommands';
import ContainerCommands from './commands/ContainerCommands';
import LayoutTreeParser from './commands/LayoutTreeParser';
import LayoutTreeCrawler from './commands/LayoutTreeCrawler';

class Navigation {
  constructor() {
    this.store = new Store();
    this.nativeEventsReceiver = new NativeEventsReceiver();
    this.uniqueIdProvider = new UniqueIdProvider();
    this.containerRegistry = new ContainerRegistry(this.store);
    this.layoutTreeParser = new LayoutTreeParser();
    this.layoutTreeCrawler = new LayoutTreeCrawler(this.uniqueIdProvider, this.store);
    this.nativeCommandsSender = new NativeCommandsSender();
    this.appCommands = new AppCommands(this.nativeCommandsSender, this.layoutTreeParser, this.layoutTreeCrawler);
  }

  registerContainer(containerName, getContainerFunc) {
    this.containerRegistry.registerContainer(containerName, getContainerFunc);
  }

  setRoot(params) {
    return this.appCommands.setRoot(params);
  }

  events() {
    return this.nativeEventsReceiver;
  }

  on(containerId) {
    return new ContainerCommands(containerId, this.nativeCommandsSender, this.layoutTreeParser, this.layoutTreeCrawler);
  }
}

const singleton = new Navigation();
module.exports = singleton;
