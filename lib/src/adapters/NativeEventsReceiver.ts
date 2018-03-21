import { NativeModules, NativeEventEmitter } from 'react-native';

export interface EventSubscription {
  remove();
}

export class NativeEventsReceiver {
  private emitter: NativeEventEmitter;
  constructor() {
    this.emitter = new NativeEventEmitter(NativeModules.RNNEventEmitter);
  }

  registerOnAppLaunched(callback: () => void): EventSubscription {
    return this.emitter.addListener('RNN.onAppLaunched', callback);
  }

  registerComponentDidAppear(callback: (componentId: string, componentName: string) => void): EventSubscription {
    return this.emitter.addListener('RNN.componentDidAppear', callback);
  }

  registerComponentDidDisappear(callback: (componentId: string, componentName: string) => void): EventSubscription {
    return this.emitter.addListener('RNN.componentDidDisappear', callback);
  }

  registerOnNavigationButtonPressed(callback: (componentId: string, buttonId: string) => void): EventSubscription {
    return this.emitter.addListener('RNN.onNavigationButtonPressed', ({ componentId, buttonId }) => callback(componentId, buttonId));
  }
}
