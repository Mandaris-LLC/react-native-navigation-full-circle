import { EventSubscription } from '../adapters/NativeEventsReceiver';

export class EventsRegistry {
  private nativeEventsReceiver;

  constructor(nativeEventsReceiver) {
    this.nativeEventsReceiver = nativeEventsReceiver;
  }

  public appLaunched(callback): EventSubscription {
    return this.nativeEventsReceiver.registerAppLaunched(callback);
  }

  public componentDidAppear(callback): EventSubscription {
    return this.nativeEventsReceiver.registerComponentDidAppear(callback);
  }

  public componentDidDisappear(callback): EventSubscription {
    return this.nativeEventsReceiver.registerComponentDidDisappear(callback);
  }

  public interaction(callback): EventSubscription {
    return this.nativeEventsReceiver.registerInteraction(callback);
  }
}
