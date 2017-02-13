import NativeCommandsSender from './adapters/NativeCommandsSender';
import NativeEventsReceiver from './adapters/NativeEventsReceiver';
import UniqueIdProvider from './adapters/UniqueIdProvider';

import Store from './containers/Store';
import ContainerRegistry from './containers/ContainerRegistry';
import AppCommands from './commands/AppCommands';
import ContainerCommands from './commands/ContainerCommands';
import LayoutTreeParser from './commands/LayoutTreeParser';
import LayoutTreeCrawler from './commands/LayoutTreeCrawler';
import PrivateEventsListener from './events/PrivateEventsListener';
import PublicEventsRegistry from './events/PublicEventsRegistry';

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
    this.publicEventsRegistry = new PublicEventsRegistry(this.nativeEventsReceiver);
    this.privateEventsListener = new PrivateEventsListener(this.nativeEventsReceiver, this.store);
    this.privateEventsListener.listenAndHandlePrivateEvents();
  }

  registerContainer(containerName, getContainerFunc) {
    this.containerRegistry.registerContainer(containerName, getContainerFunc);
  }

  setRoot(params) {
    return this.appCommands.setRoot(params);
  }

  showModal(params) {
    return this.appCommands.showModal(params);
  }

  dismissModal(id) {
    return this.appCommands.dismissModal(id);
  }

  events() {
    return this.publicEventsRegistry;
  }

  on(containerId) {
    return new ContainerCommands(containerId, this.nativeCommandsSender, this.layoutTreeParser, this.layoutTreeCrawler);
  }
}

const singleton = new Navigation();
export default singleton;
