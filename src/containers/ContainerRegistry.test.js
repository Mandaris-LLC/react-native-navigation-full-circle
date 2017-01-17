import React, {Component} from 'react';
import {AppRegistry, Text} from 'react-native';
import renderer from 'react-test-renderer';

describe('ContainerRegistry', () => {
  let uut;

  class MyContainer extends Component {
    render() {
      return <Text>{'Hello, World!'}</Text>;
    }
  }

  beforeEach(() => {
    AppRegistry.registerComponent = jest.fn(AppRegistry.registerComponent);
    const ContainerRegistry = require('./ContainerRegistry').default;
    const Store = require('./Store').default;
    uut = new ContainerRegistry(new Store());
  });

  it('registers container component by containerName into AppRegistry', () => {
    expect(AppRegistry.registerComponent).not.toHaveBeenCalled();
    uut.registerContainer('example.MyContainer.name', () => MyContainer);
    expect(AppRegistry.registerComponent).toHaveBeenCalledTimes(1);
    expect(AppRegistry.registerComponent.mock.calls[0][0]).toEqual('example.MyContainer.name');
  });

  it('resulting in a normal component', () => {
    uut.registerContainer('example.MyContainer.name', () => MyContainer);
    const Container = AppRegistry.registerComponent.mock.calls[0][1]();
    const tree = renderer.create(<Container containerId="123"/>);
    expect(tree.toJSON().children).toEqual(['Hello, World!']);
  });
});
