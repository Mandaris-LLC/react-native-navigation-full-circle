describe('Commands', () => {
  let uut;
  const nativeCommandsSender = {
    startApp: jest.fn()
  };
  const uniqueIdProvider = {
    generate: (prefix) => `${prefix}UNIQUE`
  };

  beforeEach(() => {
    const Commands = require('./Commands').default;
    uut = new Commands(nativeCommandsSender, uniqueIdProvider);
  });

  describe('startApp', () => {
    it('sends startApp to native after parsing into layoutTree', () => {
      uut.startApp({
        container: {
          name: 'com.example.MyScreen'
        }
      });
      expect(nativeCommandsSender.startApp).toHaveBeenCalledTimes(1);
      //TODO
    });
  });
});
