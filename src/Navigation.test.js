describe('Navigation', () => {
  let Navigation;
  let ContainerRegistry, Commands;
  let mockNativeEventsReceiver;

  beforeEach(() => {
    jest.mock('./containers/ContainerRegistry');
    jest.mock('./commands/Commands');
    ContainerRegistry = require('./containers/ContainerRegistry');
    Commands = require('./commands/Commands');

    mockNativeEventsReceiver = {
      onAppLaunched: jest.fn()
    };

    jest.mock('./adapters/NativeEventsReceiver', () => () => mockNativeEventsReceiver);

    Navigation = require('./Navigation');
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

  it('onAppLaunched delegates to NativeEventsReceiver', () => {
    Navigation.onAppLaunched(jest.fn());
    expect(mockNativeEventsReceiver.onAppLaunched).toHaveBeenCalledTimes(1);
  });
});
