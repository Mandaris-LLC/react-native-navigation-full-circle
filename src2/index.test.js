describe('index', () => {
  let uut;

  beforeEach(() => {
    uut = require('./index');
  });

  it('exposes Navigation as default or as Object', () => {
    expect(uut.Navigation).toBeDefined();
    expect(uut.Navigation).toEqual(uut.default);
    expect(uut.Navigation.startApp).toBeInstanceOf(Function);
    expect(uut.default.startApp).toBeInstanceOf(Function);
  });
});
