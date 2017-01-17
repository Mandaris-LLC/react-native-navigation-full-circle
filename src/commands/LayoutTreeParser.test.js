import * as SimpleLayouts from './SimpleLayouts';

describe('LayoutTreeParser', () => {
  let uut;

  beforeEach(() => {
    const uniqueIdProvider = {generate: (p) => `${p}UNIQUE_ID`};
    const LayoutTreeParser = require('./LayoutTreeParser').default;
    uut = new LayoutTreeParser(uniqueIdProvider);
  });

  it('returns a deep clone', () => {
    const input = {inner: {foo: {bar: 1}}};
    expect(uut.parse(input)).not.toBe(input);
    expect(uut.parse(input).inner).not.toBe(input.inner);
    expect(uut.parse(input).inner.foo).not.toBe(input.inner.foo);
  });

  it('adds uniqueId to containers', () => {
    const input = {container: {}};
    expect(uut.parse(input)).toEqual({container: {id: 'containerUNIQUE_ID'}});
  });

  it('parses simple structures', () => {
    uut.parse(SimpleLayouts.singleScreenApp);
    //TODO
  });
});
