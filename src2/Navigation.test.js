import _ from 'lodash';

describe('Navigation', () => {
  let Navigation;
  let Commands;
  let ContainerRegistry;

  beforeEach(() => {
    jest.mock('./containers/ContainerRegistry');
    Navigation = require('./Navigation');
    Commands = require('./commands/Commands');
    ContainerRegistry = require('./containers/ContainerRegistry');
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

  it('delegates register container to container registry', () => {
    expect(ContainerRegistry.registerContainer).not.toHaveBeenCalled();
    const fn = jest.fn();
    Navigation.registerContainer('key', fn);
    expect(ContainerRegistry.registerContainer).toHaveBeenCalledTimes(1);
    expect(ContainerRegistry.registerContainer).toHaveBeenCalledWith('key', fn);
  });
});
