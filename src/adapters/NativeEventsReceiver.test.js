import { NativeModules } from 'react-native';
import NativeEventsReceiver from './NativeEventsReceiver';

describe('NativeEventsReceiver', () => {
  let uut;
  const eventEmitterMock = { addListener: jest.fn() };

  beforeEach(() => {
    NativeModules.RNNEventEmitter = {};
    uut = new NativeEventsReceiver();
    uut.emitter = eventEmitterMock;
  });

  it('register for onAppLaunched', () => {
    const callback = jest.fn();
    uut.onAppLaunched(callback);
    expect(callback).not.toHaveBeenCalled();
    expect(eventEmitterMock.addListener).toHaveBeenCalledTimes(1);
    expect(eventEmitterMock.addListener).toHaveBeenCalledWith('RNN_onAppLaunched', callback);
  });
});
