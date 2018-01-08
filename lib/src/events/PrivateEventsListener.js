const Lifecycle = require('../components/Lifecycle');

class PrivateEventsListener {
  constructor(nativeEventsReceiver, store) {
    this.nativeEventsReceiver = nativeEventsReceiver;
    this.lifecycle = new Lifecycle(store);
  }

  listenAndHandlePrivateEvents() {
    this.nativeEventsReceiver.componentDidAppear(this.lifecycle.componentDidAppear);
    this.nativeEventsReceiver.componentDidDisappear(this.lifecycle.componentDidDisappear);
    this.nativeEventsReceiver.navigationButtonPressed(this.lifecycle.onNavigationButtonPressed);
  }
}

module.exports = PrivateEventsListener;
