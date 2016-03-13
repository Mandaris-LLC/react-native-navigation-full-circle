import { Component, NativeAppEventEmitter } from 'react-native';
import platformSpecific from './platformSpecific';
import Navigation from './Navigation';

class Navigator {
  constructor(navigatorID) {
    this.navigatorID = navigatorID;
  }
  push(params = {}) {
    return platformSpecific.navigatorPush(this, params);
  }
  pop(params = {}) {
    return platformSpecific.navigatorPop(this, params);
  }
  showModal(params = {}) {
    return Navigation.showModal(params);
  }
  dismissModal(params = {}) {
    return Navigation.dismissModal(params);
  }
}

export default class Screen extends Component {
  static navigatorStyle = {};
  static navigatorButtons = {};
  constructor(props) {
    super(props);
    if (props.navigatorID) {
      this.navigator = new Navigator(props.navigatorID);
    }
    if (props.navigatorEventID) {
      this.navigatorEventSubscription = NativeAppEventEmitter.addListener(props.navigatorEventID, (event) => this.onNavigatorEvent(event));
    }
  }
  onNavigatorEvent(event) {}
  componentWillUnmount() {
    if (this.navigatorEventSubscription) {
      this.navigatorEventSubscription.remove();
    }
  }
}
