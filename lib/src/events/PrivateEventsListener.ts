import * as Lifecycle from '../components/Lifecycle';

export class PrivateEventsListener {
  private nativeEventsReceiver: any;
  private lifecycle: any;

  constructor(nativeEventsReceiver: any, store: any) {
    this.nativeEventsReceiver = nativeEventsReceiver;
    this.lifecycle = new Lifecycle(store);
  }

  listenAndHandlePrivateEvents() {
    this.nativeEventsReceiver.registerComponentDidAppear(this.lifecycle.componentDidAppear);
    this.nativeEventsReceiver.registerComponentDidDisappear(this.lifecycle.componentDidDisappear);
    this.nativeEventsReceiver.registerNavigationButtonPressed(this.lifecycle.onNavigationButtonPressed);
  }
}
