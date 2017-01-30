describe('NativeCommandsSender', () => {
  let uut, mockNativeModule;

  beforeEach(() => {
    mockNativeModule = {
      setRoot: jest.fn()
    };
    require('react-native').NativeModules.RNNBridgeModule = mockNativeModule;
    const NativeCommandsSender = require('./NativeCommandsSender').default;
    uut = new NativeCommandsSender();
  });

  it('delegates to native', () => {
    uut.setRoot();
    expect(mockNativeModule.setRoot).toHaveBeenCalledTimes(1);
  });
});
