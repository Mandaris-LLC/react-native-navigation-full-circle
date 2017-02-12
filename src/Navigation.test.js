describe('Navigation', () => {
  let Navigation;

  beforeEach(() => {
    //TODO don't mock here, just create the actual object and assert on basic behavior
    jest.mock('./adapters/UniqueIdProvider');
    jest.mock('./adapters/NativeCommandsSender');
    jest.mock('./adapters/NativeEventsReceiver');

    jest.mock('./containers/ContainerRegistry');
    jest.mock('./commands/AppCommands');

    Navigation = require('./Navigation').default;
  });

  it('registerContainer delegates to ContainerRegistry', () => {
    expect(Navigation.containerRegistry.registerContainer).not.toHaveBeenCalled();
    const fn = jest.fn();
    Navigation.registerContainer('name', fn);
    expect(Navigation.containerRegistry.registerContainer).toHaveBeenCalledTimes(1);
    expect(Navigation.containerRegistry.registerContainer).toHaveBeenCalledWith('name', fn);
  });

  it('setRoot delegates to Commands', async () => {
    Navigation.appCommands.setRoot.mockReturnValue(Promise.resolve('result'));
    const params = {};
    const result = await Navigation.setRoot(params);
    expect(result).toEqual('result');
    expect(Navigation.appCommands.setRoot).toHaveBeenCalledTimes(1);
    expect(Navigation.appCommands.setRoot).toHaveBeenCalledWith(params);
  });

  it('showModal delegates to AppCommands', async () => {
    Navigation.appCommands.showModal.mockReturnValue(Promise.resolve('result'));
    const params = {};
    const result = await Navigation.showModal(params);
    expect(result).toEqual('result');
    expect(Navigation.appCommands.showModal).toHaveBeenCalledTimes(1);
    expect(Navigation.appCommands.showModal).toHaveBeenCalledWith(params);
  });

  it('events return public events', () => {
    const cb = jest.fn();
    Navigation.events().onAppLaunched(cb);
    expect(Navigation.nativeEventsReceiver.appLaunched).toHaveBeenCalledTimes(1);
    expect(Navigation.nativeEventsReceiver.appLaunched).toHaveBeenCalledWith(cb);
  });

  it('on containerId returns an object that performs commands on a container', () => {
    expect(Navigation.on('myUniqueId').push).toBeInstanceOf(Function);
  });

  it('starts listening and handles internal events', () => {
    expect(Navigation.nativeEventsReceiver.containerStart).toHaveBeenCalledTimes(1);
  });
});
