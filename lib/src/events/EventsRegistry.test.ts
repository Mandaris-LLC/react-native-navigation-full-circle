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
    mockNativeEventsReceiver.registerAppLaunched.mockReturnValueOnce(subscription);

    const result = uut.appLaunched(cb);

    expect(result).toBe(subscription);
    expect(mockNativeEventsReceiver.registerAppLaunched).toHaveBeenCalledTimes(1);
    expect(mockNativeEventsReceiver.registerAppLaunched).toHaveBeenCalledWith(cb);
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
});
