describe('PropsStore', () => {
  let uut;

  beforeEach(() => {
    uut = require('./PropsStore');
  });

  it('initial state', () => {
    expect(uut.getPropsForScreenId('screen1')).toEqual({});
  });

  it('holds props by screenId', () => {
    uut.setPropsForScreenId('screen1', {a: 1, b: 2});
    expect(uut.getPropsForScreenId('screen1')).toEqual({a: 1, b: 2});
  });

  it('defensive for invalid screenId and props', () => {
    uut.setPropsForScreenId('screen1', undefined);
    uut.setPropsForScreenId(undefined, undefined);
    expect(uut.getPropsForScreenId('screen1')).toEqual({});
  });
});
