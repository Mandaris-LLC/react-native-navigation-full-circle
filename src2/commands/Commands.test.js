describe('Commands', () => {
  let uut;

  beforeEach(() => {
    uut = require('./Commands');
  });

  describe('startApp', () => {
    it('receives params object', () => {
      uut.startApp({
        screenKey: {
          screen: 'example.MyScreen'
        },
        drawer: {
          left: {
            screen: 'example.SideMenu'
          }
        }
      });
    });

    it('expects to get screenKey, or tabs with screenKeys', () => {
      expect(() => uut.startApp({screenKey: 'example.MyScreen'})).not.toThrow();
      expect(() => uut.startApp({tabs: [{screenKey: 'example.Tab1'}]})).not.toThrow();
      expect(() => uut.startApp()).toThrow();
      expect(() => uut.startApp({})).toThrow();
      expect(() => uut.startApp({tabs: []})).toThrow();
      expect(() => uut.startApp({tabs: [{}]})).toThrow();
      expect(() => uut.startApp({tabs: [{screenKey: 'example.Tab1'}, {}]})).toThrow();
    });
  });
});
