describe('Navigation', () => {
  let Navigation;

  beforeEach(() => {
    jest.mock('./adapters/UniqueIdProvider');
    jest.mock('./adapters/NativeCommandsSender');
    jest.mock('./adapters/NativeEventsReceiver');

    jest.mock('./containers/ContainerRegistry');
    jest.mock('./commands/Commands');

    Navigation = require('./Navigation');
  });

  it('registerContainer delegates to ContainerRegistry', () => {
    expect(Navigation.containerRegistry.registerContainer).not.toHaveBeenCalled();
    const fn = jest.fn();
    Navigation.registerContainer('name', fn);
    expect(Navigation.containerRegistry.registerContainer).toHaveBeenCalledTimes(1);
    expect(Navigation.containerRegistry.registerContainer).toHaveBeenCalledWith('name', fn);
  });

  it('startApp delegates to Commands', () => {
    const params = {};
    Navigation.startApp(params);
    expect(Navigation.commands.startApp).toHaveBeenCalledTimes(1);
    expect(Navigation.commands.startApp).toHaveBeenCalledWith(params);
  });

  it('onAppLaunched delegates to NativeEventsReceiver', () => {
    const fn = jest.fn();
    Navigation.onAppLaunched(fn);
    expect(Navigation.nativeEventsReceiver.onAppLaunched).toHaveBeenCalledTimes(1);
    expect(Navigation.nativeEventsReceiver.onAppLaunched).toHaveBeenCalledWith(fn);
  });
});
