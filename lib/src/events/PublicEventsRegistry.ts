export class PublicEventsRegistry {
  private nativeEventsReceiver;

  constructor(nativeEventsReceiver) {
    this.nativeEventsReceiver = nativeEventsReceiver;
  }

  onAppLaunched(callback) {
    this.nativeEventsReceiver.registerAppLaunched(callback);
  }
}
