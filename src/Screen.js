import { Component, NativeAppEventEmitter } from 'react-native';
import platformSpecific from './platformSpecific';
import Navigation from './Navigation';

class Navigator {
  constructor(navigatorID, navigatorEventID) {
    this.navigatorID = navigatorID;
    this.navigatorEventID = navigatorEventID;
    this.navigatorEventHandler = null;
    this.navigatorEventSubscription = null;
  }
  push(params = {}) {
    return platformSpecific.navigatorPush(this, params);
  }
  pop(params = {}) {
    return platformSpecific.navigatorPop(this, params);
  }
  popToRoot(params = {}) {
    return platformSpecific.navigatorPopToRoot(this, params);
  }
  resetTo(params = {}) {
    return platformSpecific.navigatorResetTo(this, params);
  }
  showModal(params = {}) {
    return Navigation.showModal(params);
  }
  dismissModal(params = {}) {
    return Navigation.dismissModal(params);
  }
  setButtons(params = {}) {
    return platformSpecific.navigatorSetButtons(this, this.navigatorEventID, params);
  }
  setTitle(params = {}) {
    return platformSpecific.navigatorSetTitle(this, params);
  }
  toggleDrawer(params = {}) {
    return platformSpecific.navigatorToggleDrawer(this, params);
  }
  setOnNavigatorEvent(callback) {
    this.navigatorEventHandler = callback;
    if (!this.navigatorEventSubscription) {
      this.navigatorEventSubscription = NativeAppEventEmitter.addListener(this.navigatorEventID, (event) => this.onNavigatorEvent(event));
    }
  }
  onNavigatorEvent(event) {
    if (this.navigatorEventHandler) {
      this.navigatorEventHandler(event);
    }
  }
  cleanup() {
    if (this.navigatorEventSubscription) {
      this.navigatorEventSubscription.remove();
    }
  }
}

export default class Screen extends Component {
  static navigatorStyle = {};
  static navigatorButtons = {};
  constructor(props) {
    super(props);
    if (props.navigatorID) {
      this.navigator = new Navigator(props.navigatorID, props.navigatorEventID);
    }
  }
  componentWillUnmount() {
    if (this.navigator) {
      this.navigator.cleanup();
      this.navigator = undefined;
    }
  }
}
