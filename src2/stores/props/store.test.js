describe('stores/props/store', () => {
  let uut;

  beforeEach(() => {
    uut = require('./store');
  });

  it('initial state', () => {
    expect(uut.selectors.getPropsForScreenId('screen1')).toEqual({});
  });

  it('holds props by screenId', () => {
    uut.mutators.setPropsForScreenId('screen1', {a: 1, b: 2});
    expect(uut.selectors.getPropsForScreenId('screen1')).toEqual({a: 1, b: 2});
  });

  it('defensive for invalid screenId and props', () => {
    uut.mutators.setPropsForScreenId('screen1', undefined);
    uut.mutators.setPropsForScreenId(undefined, undefined);
    expect(uut.selectors.getPropsForScreenId('screen1')).toEqual({});
  });
});
