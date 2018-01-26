export class PublicEventsRegistry {
  private nativeEventsReceiver;

  constructor(nativeEventsReceiver) {
    this.nativeEventsReceiver = nativeEventsReceiver;
  }

  onAppLaunched(callback) {
    this.nativeEventsReceiver.registerAppLaunched(callback);
  }

  navigationCommands(callback) {
    this.nativeEventsReceiver.registerNavigationCommands(callback);
  }

  componentLifecycle(callback) {
    this.nativeEventsReceiver.registerComponentLifecycle(callback);
  }
}
