import _ from 'lodash';

describe('Navigation', () => {
  let Navigation;

  beforeEach(() => {
    Navigation = require('./Navigation');
  });

  it('exposes static commands', () => {
    _.forEach([
      Navigation.registerContainer,
      Navigation.startApp,
      Navigation.push,
      Navigation.pop,
      Navigation.showModal,
      Navigation.dismissModal,
      Navigation.dismissAllModals,
      Navigation.showLightbox,
      Navigation.dismissLightbox,
      Navigation.showInAppNotification,
      Navigation.dismissInAppNotification
    ], (f) => expect(f).toBeInstanceOf(Function));
  });
});
