import { Constants } from './Constants';

const NavigationModule = {
  backButton: 'RNN.back',
  statusBarHeight: 63
};

describe('Constants', () => {
  let uut: Constants;

  beforeEach(() => {
    uut = new Constants(NavigationModule);
  });

  it('backButton', () => {
    expect(uut.backButton).toEqual(NavigationModule.backButton);
  });

  it('statusBarHeight', () => {
    expect(uut.statusBarHeight).toEqual(NavigationModule.statusBarHeight);
  });
});
