describe('ComponentRegistry', () => {
  let uut;
  let AppRegistry;
  let MyContainer, Component;

  beforeEach(() => {
    AppRegistry = {registerComponent: jest.fn()};
    Component = class {
      //
    };

    jest.mock('react', () => ({}));
    jest.mock('react-native', () => ({AppRegistry, Component}));
    uut = require('./ContainerRegistry');

    MyContainer = class extends Component {
      //
    };
  });

  it('registers container component into AppRegistry', () => {
    expect(AppRegistry.registerComponent).not.toHaveBeenCalled();

    uut.registerContainer('example.MyContainer', () => MyContainer);

    expect(AppRegistry.registerComponent).toHaveBeenCalledTimes(1);
    expect(AppRegistry.registerComponent.mock.calls[0][0]).toEqual('example.MyContainer');
  });
});
