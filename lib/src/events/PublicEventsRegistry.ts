export class PublicEventsRegistry {
  private nativeEventsReceiver: any;

  constructor(nativeEventsReceiver) {
    this.nativeEventsReceiver = nativeEventsReceiver;
  }

  onAppLaunched(callback) {
    this.nativeEventsReceiver.registerAppLaunched(callback);
  }
}
