import { EventsRegistry } from './EventsRegistry';
import { NativeEventsReceiver } from '../adapters/NativeEventsReceiver.mock';

describe('EventsRegistry', () => {
  let uut: EventsRegistry;
  let mockNativeEventsReceiver;

  beforeEach(() => {
    mockNativeEventsReceiver = new NativeEventsReceiver();
    uut = new EventsRegistry(mockNativeEventsReceiver);
  });

  it('exposes appLaunch event', () => {
    const subscription = {};
    const cb = jest.fn();
    mockNativeEventsReceiver.registerOnAppLaunched.mockReturnValueOnce(subscription);

    const result = uut.onAppLaunched(cb);

    expect(result).toBe(subscription);
    expect(mockNativeEventsReceiver.registerOnAppLaunched).toHaveBeenCalledTimes(1);
    expect(mockNativeEventsReceiver.registerOnAppLaunched).toHaveBeenCalledWith(cb);
  });

  it('exposes componentDidAppear event', () => {
    const subscription = {};
    const cb = jest.fn();
    mockNativeEventsReceiver.registerComponentDidAppear.mockReturnValueOnce(subscription);

    const result = uut.componentDidAppear(cb);

    expect(result).toBe(subscription);
    expect(mockNativeEventsReceiver.registerComponentDidAppear).toHaveBeenCalledTimes(1);
    expect(mockNativeEventsReceiver.registerComponentDidAppear).toHaveBeenCalledWith(cb);
  });

  it('exposes componentDidDisappear event', () => {
    const subscription = {};
    const cb = jest.fn();
    mockNativeEventsReceiver.registerComponentDidDisappear.mockReturnValueOnce(subscription);

    const result = uut.componentDidDisappear(cb);

    expect(result).toBe(subscription);
    expect(mockNativeEventsReceiver.registerComponentDidDisappear).toHaveBeenCalledTimes(1);
    expect(mockNativeEventsReceiver.registerComponentDidDisappear).toHaveBeenCalledWith(cb);
  });

  it('exposes interaction event', () => {
    const subscription = {};
    const cb = jest.fn();
    mockNativeEventsReceiver.registerOnNavigationInteraction.mockReturnValueOnce(subscription);

    const result = uut.onNavigationInteraction(cb);

    expect(result).toBe(subscription);
    expect(mockNativeEventsReceiver.registerOnNavigationInteraction).toHaveBeenCalledTimes(1);
    expect(mockNativeEventsReceiver.registerOnNavigationInteraction).toHaveBeenCalledWith(cb);
  });
});
