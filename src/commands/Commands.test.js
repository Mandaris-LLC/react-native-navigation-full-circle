describe('Commands', () => {
  let uut;
  let mockNativeNavigation;
  let mockUniqueIdProvider;

  beforeEach(() => {
    mockNativeNavigation = {
      startApp: jest.fn()
    };
    require('react-native').NativeModules.NativeNavigation = mockNativeNavigation;
    jest.mock('../providers/UniqueIdProvider');
    mockUniqueIdProvider = require('../providers/UniqueIdProvider');
    uut = require('./Commands');
  });

  describe('startApp', () => {
    it('sends startApp to native', () => {
      uut.startApp({
        container: {
          key: 'example.MyContainer'
        }
      });
      expect(mockNativeNavigation.startApp).toHaveBeenCalledTimes(1);
    });

    it('parses the params and construct single screen hirarchy', () => {
      mockUniqueIdProvider.uniqueId = jest.fn((prefix) => `${prefix}123`);

      expect(uut.parse(
        {
          container: {
            key: 'com.example.MyScreen'
          }
        })).toEqual(
        {
          containerStack: {
            id: 'containerStack123',
            stack: [
              {
                container: {
                  key: 'com.example.MyScreen',
                  id: 'container123'
                }
              }
            ]
          }
        });
    });
    //it('receives params object', () => {
    //  uut.startApp({
    //    container: {
    //      key: 'example.MyContainer'
    //    },
    //    drawer: {
    //      left: {
    //        key: 'example.SideMenu'
    //      }
    //    }
    //  });
    //});
    //it('expects to get containerKey, or tabs with containerKeys', () => {
    //  expect(() => uut.startApp({containerKey: 'example.MyContainer'})).not.toThrow();
    //  expect(() => uut.startApp({tabs: [{containerKey: 'example.Tab1'}]})).not.toThrow();
    //  expect(() => uut.startApp()).toThrow();
    //  expect(() => uut.startApp({})).toThrow();
    //  expect(() => uut.startApp({tabs: []})).toThrow();
    //  expect(() => uut.startApp({tabs: [{}]})).toThrow();
    //  expect(() => uut.startApp({tabs: [{containerKey: 'example.Tab1'}, {}]})).toThrow();
    //});
  });
});
