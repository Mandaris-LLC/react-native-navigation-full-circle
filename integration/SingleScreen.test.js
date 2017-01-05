describe('single screen integration', () => {
  let Navigation;
  let mockNativeNavigation;

  beforeEach(() => {
    mockNativeNavigation = {};
    require('react-native').NativeModules.NativeNavigation = mockNativeNavigation;
    Navigation = require('../../src/index').default;
  });

  xit('startApp directs to native with constructed hirarchy for single screens', () => {
    mockNativeNavigation.startApp = jest.fn();
    Navigation.startApp({
      root: {
        key: 'com.integration.MyFirstScreen'
      }
    });
    expect(mockNativeNavigation.startApp).toHaveBeenCalledTimes(1);
  });
});
