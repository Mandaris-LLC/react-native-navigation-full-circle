import _ from 'lodash';

describe('Navigation', () => {
  let Navigation;
  let Commands;
  let ScreenRegistry;

  beforeEach(() => {
    jest.mock('./screens/ScreenRegistry');
    Navigation = require('./Navigation');
    Commands = require('./commands/Commands');
    ScreenRegistry = require('./screens/ScreenRegistry');
  });

  it('exposes static commands', () => {
    _.forEach([
      Navigation.registerScreen,
      Navigation.startApp,
      Navigation.push,
      Navigation.pop,
      Navigation.popToRoot,
      Navigation.newStack,
      Navigation.showModal,
      Navigation.dismissModal,
      Navigation.dismissAllModals,
      Navigation.showLightbox,
      Navigation.dismissLightbox,
      Navigation.showInAppNotification,
      Navigation.dismissInAppNotification
    ], (f) => expect(f).toBeInstanceOf(Function));
  });

  it('delegates register screen to screen registry', () => {
    expect(ScreenRegistry.registerScreen).not.toHaveBeenCalled();
    const fn = jest.fn();
    Navigation.registerScreen('key', fn);
    expect(ScreenRegistry.registerScreen).toHaveBeenCalledTimes(1);
    expect(ScreenRegistry.registerScreen).toHaveBeenCalledWith('key', fn);
  });
});
