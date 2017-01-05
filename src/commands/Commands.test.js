describe('Commands', () => {
  let uut;

  beforeEach(() => {
    uut = require('./Commands');
  });

  describe('startApp', () => {
    it('receives params object', () => {
      uut.startApp({
        containerKey: {
          root: 'example.MyContainer'
        },
        drawer: {
          left: {
            root: 'example.SideMenu'
          }
        }
      });
    });

    it('expects to get containerKey, or tabs with containerKeys', () => {
      expect(() => uut.startApp({containerKey: 'example.MyContainer'})).not.toThrow();
      expect(() => uut.startApp({tabs: [{containerKey: 'example.Tab1'}]})).not.toThrow();
      expect(() => uut.startApp()).toThrow();
      expect(() => uut.startApp({})).toThrow();
      expect(() => uut.startApp({tabs: []})).toThrow();
      expect(() => uut.startApp({tabs: [{}]})).toThrow();
      expect(() => uut.startApp({tabs: [{containerKey: 'example.Tab1'}, {}]})).toThrow();
    });
  });
});
