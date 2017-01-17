describe('UniqueIdProvider', () => {
  let uut;

  beforeEach(() => {
    uut = require('./UniqueIdProvider');
  });

  it('provides uniqueId', () => {
    expect(uut.uniqueId()).toEqual('1');
    expect(uut.uniqueId()).toEqual('2');
  });

  it('provides with prefix', () => {
    expect(uut.uniqueId('prefix')).toEqual('prefix1');
    expect(uut.uniqueId('prefix')).toEqual('prefix2');
    expect(uut.uniqueId('other')).toEqual('other3');
  });
});
