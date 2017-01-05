import Navigation from './index';

describe('index', () => {
  let uut;

  beforeEach(() => {
    uut = require('./index').default;
  });

  it('exposes Navigation as default', () => {
    expect(uut.startApp).toBeInstanceOf(Function);
    expect(Navigation.startApp).toBeInstanceOf(Function);
  });
});
