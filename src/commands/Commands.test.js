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

    it('adds uniqueId to passed container', () => {
      uut.startApp({
        container: {
          key: 'com.example.MyScreen'
        }
      });
      expect(mockNativeNavigation.startApp).toHaveBeenCalledWith({
        container: {
          key: 'com.example.MyScreen',
          id: 'container123'
        }
      });
    });
  });
});
