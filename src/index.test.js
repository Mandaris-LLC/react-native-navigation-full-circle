import Navigation from './index';

describe('index', () => {
  let uut;

  beforeEach(() => {
    uut = require('./index');
  });

  it('exposes Navigation', () => {
    expect(uut.startApp).toBeInstanceOf(Function);
    expect(Navigation.startApp).toBeInstanceOf(Function);
  });
});
