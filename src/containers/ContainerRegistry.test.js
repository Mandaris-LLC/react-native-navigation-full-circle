import React, { Component } from 'react';
import { AppRegistry, Text } from 'react-native';
import renderer from 'react-test-renderer';

import ContainerRegistry from './ContainerRegistry';
import Store from './Store';

describe('ContainerRegistry', () => {
  let uut;

  class MyContainer extends Component {
    render() {
      return <Text>{'Hello, World!'}</Text>;
    }
  }

  beforeEach(() => {
    AppRegistry.registerComponent = jest.fn(AppRegistry.registerComponent);
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
    const tree = renderer.create(<Container id="123" />);
    expect(tree.toJSON().children).toEqual(['Hello, World!']);
  });
});
