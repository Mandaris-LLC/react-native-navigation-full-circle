describe('Commands', () => {
  let uut;
  let mockNativeNavigation;

  beforeEach(() => {
    mockNativeNavigation = {
      startApp: jest.fn()
    };
    require('react-native').NativeModules.NativeNavigation = mockNativeNavigation;
    jest.mock('../providers/UniqueIdProvider');
    uut = require('./Commands');
  });

  describe('startApp', () => {
    it('sends startApp to native', () => {
      uut.startApp({
        container: {
          key: 'com.example.MyScreen'
        }
      });
      expect(mockNativeNavigation.startApp).toHaveBeenCalledTimes(1);
    });
  });
});
