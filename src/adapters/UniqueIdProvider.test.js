describe('UniqueIdProvider', () => {
  let uut;

  beforeEach(() => {
    const UniqueIdProvider = require('./UniqueIdProvider').default;
    uut = new UniqueIdProvider();
  });

  it('provides uniqueId', () => {
    expect(uut.generate()).toEqual('1');
    expect(uut.generate()).toEqual('2');
  });

  it('provides with prefix', () => {
    expect(uut.generate('prefix')).toEqual('prefix1');
    expect(uut.generate('prefix')).toEqual('prefix2');
    expect(uut.generate('other')).toEqual('other3');
  });
});
