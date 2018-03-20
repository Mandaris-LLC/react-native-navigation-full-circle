import { EventSubscription, NativeEventsReceiver } from '../adapters/NativeEventsReceiver';

export class EventsRegistry {
  private nativeEventsReceiver: NativeEventsReceiver;

  constructor(nativeEventsReceiver: NativeEventsReceiver) {
    this.nativeEventsReceiver = nativeEventsReceiver;
  }

  public appLaunched(callback: () => void): EventSubscription {
    return this.nativeEventsReceiver.registerAppLaunched(callback);
  }

  public componentDidAppear(callback: (componendId: string, componentName: string) => void): EventSubscription {
    return this.nativeEventsReceiver.registerComponentDidAppear(callback);
  }

  public componentDidDisappear(callback: (componendId: string, componentName: string) => void): EventSubscription {
    return this.nativeEventsReceiver.registerComponentDidDisappear(callback);
  }

  public interaction(callback: (name: string) => void): EventSubscription {
    return this.nativeEventsReceiver.registerInteraction(callback);
  }
}
