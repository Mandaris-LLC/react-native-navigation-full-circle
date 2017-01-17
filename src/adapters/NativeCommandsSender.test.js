describe('NativeCommandsSender', () => {
  let uut, mockNativeModule;

  beforeEach(() => {
    mockNativeModule = {
      startApp: jest.fn()
    };
    require('react-native').NativeModules.RNNBridgeModule = mockNativeModule;
    const NativeCommandsSender = require('./NativeCommandsSender').default;
    uut = new NativeCommandsSender();
  });

  it('delegates to native', () => {
    uut.startApp();
    expect(mockNativeModule.startApp).toHaveBeenCalledTimes(1);
  });
});
