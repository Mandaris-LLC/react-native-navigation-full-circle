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
    mockNativeEventsReceiver.registerAppLaunched.mockReturnValueOnce(subscription);
    const cb = jest.fn();
    const result = uut.appLaunched(cb);
    expect(mockNativeEventsReceiver.registerAppLaunched).toHaveBeenCalledTimes(1);
    expect(mockNativeEventsReceiver.registerAppLaunched).toHaveBeenCalledWith(cb);
    expect(subscription).toBe(result);
  });
});
