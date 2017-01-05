import Navigation from './index';
import {Navigation as inner} from './index';

describe('index', () => {
  let uut;

  beforeEach(() => {
    uut = require('./index').default;
  });

  it('exposes Navigation as default or as object', () => {
    expect(uut.startApp).toBeInstanceOf(Function);
    expect(Navigation.startApp).toBeInstanceOf(Function);
    expect(inner.startApp).toBeInstanceOf(Function);
  });
});
