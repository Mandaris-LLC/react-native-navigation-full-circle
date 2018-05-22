import { Constants } from './Constants';

const NavigationModule = {
  backButton: 'backButton'
};

describe('Constants', () => {
  let uut: Constants;

  beforeEach(() => {
    uut = new Constants(NavigationModule);
  });

  it('backButton', () => {
    expect(uut.backButton).toEqual(NavigationModule.backButton);
  });
});
