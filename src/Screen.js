import { Component, NativeAppEventEmitter } from 'react-native';
import platformSpecific from './platformSpecific';
import Navigation from './Navigation';

class Navigator {
  constructor(navigatorID, screenInstance) {
    this.navigatorID = navigatorID;
    this.screenInstance = screenInstance;
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
    const navigatorEventID = this.screenInstance.listenOnNavigatorEvents();
    return platformSpecific.navigatorSetButtons(this, navigatorEventID, params);
  }
  setTitle(params = {}) {
    return platformSpecific.navigatorSetTitle(this, params);
  }
  toggleDrawer(params = {}) {
    return platformSpecific.navigatorToggleDrawer(this, params);
  }
}

export default class Screen extends Component {
  static navigatorStyle = {};
  static navigatorButtons = {};
  constructor(props) {
    super(props);
    if (props.navigatorID) {
      this.navigator = new Navigator(props.navigatorID, this);
    }
    if (props.listenForEvents) {
      this.listenOnNavigatorEvents();
    }
  }
  listenOnNavigatorEvents() {
    if (!this.navigatorEventSubscription) {
      this.navigatorEventSubscription = NativeAppEventEmitter.addListener(this.props.navigatorEventID, (event) => this.onNavigatorEvent(event));
    }
    return this.props.navigatorEventID;
  }
  onNavigatorEvent(event) {}
  componentWillUnmount() {
    this.navigator = undefined;
    if (this.navigatorEventSubscription) {
      this.navigatorEventSubscription.remove();
    }
  }
}
