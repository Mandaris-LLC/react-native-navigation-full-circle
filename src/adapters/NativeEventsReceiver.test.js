describe('NativeEventsReceiver', () => {
  let uut;
  const eventEmitterMock = {addListener: jest.fn()};

  beforeEach(() => {
    require('react-native').NativeModules.RNNEventEmitter = {};
    const NativeEventsReceiver = require('./NativeEventsReceiver').default;
    uut = new NativeEventsReceiver();
    uut.emitter = eventEmitterMock;
  });

  it('register for onAppLaunched', () => {
    const callback = jest.fn();
    uut.onAppLaunched(callback);
    expect(callback).not.toHaveBeenCalled();
    expect(eventEmitterMock.addListener).toHaveBeenCalledTimes(1);
    expect(eventEmitterMock.addListener).toHaveBeenCalledWith('onAppLaunched', callback);
  });
});
