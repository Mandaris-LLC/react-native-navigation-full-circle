import _ from 'lodash';

describe('Navigation', () => {
  let Navigation;
  let ContainerRegistry, Commands;

  beforeEach(() => {
    jest.mock('./containers/ContainerRegistry');
    jest.mock('./commands/Commands');
    ContainerRegistry = require('./containers/ContainerRegistry');
    Commands = require('./commands/Commands');
    Navigation = require('./Navigation');
  });

  it('exposes static commands', () => {
    _.forEach([
      Navigation.registerContainer,
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

  it('registerContainer delegates to ContainerRegistry', () => {
    expect(ContainerRegistry.registerContainer).not.toHaveBeenCalled();
    const fn = jest.fn();
    Navigation.registerContainer('name', fn);
    expect(ContainerRegistry.registerContainer).toHaveBeenCalledTimes(1);
    expect(ContainerRegistry.registerContainer).toHaveBeenCalledWith('name', fn);
  });

  it('startApp delegates to Commands', () => {
    const params = {};
    Navigation.startApp(params);
    expect(Commands.startApp).toHaveBeenCalledTimes(1);
    expect(Commands.startApp).toHaveBeenCalledWith(params);
  });
});
