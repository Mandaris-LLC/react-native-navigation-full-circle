import {AppRegistry, Text} from 'react-native';
import React, {Component} from 'react';

class MyContainer extends Component {
  render() {
    return (
      <Text>{'Hello, World!'}</Text>
    );
  }
}

import renderer from 'react-test-renderer';

describe('ComponentRegistry', () => {
  let uut;

  beforeEach(() => {
    AppRegistry.registerComponent = jest.fn(AppRegistry.registerComponent);
    //jest.mock('react-native', () => ({AppRegistry}));
    uut = require('./ContainerRegistry');
  });

  xit('registers container component into AppRegistry', () => {
    expect(AppRegistry.registerComponent).not.toHaveBeenCalled();

    uut.registerContainer('example.MyContainer', () => MyContainer);

    expect(AppRegistry.registerComponent).toHaveBeenCalledTimes(1);
    expect(AppRegistry.registerComponent.mock.calls[0][0]).toEqual('example.MyContainer');
  });

  it('wraps the container', () => {
    uut.registerContainer('example.MyContainer', () => MyContainer);

    const WrappedClass = AppRegistry.registerComponent.mock.calls[0][1]();
    const tree = renderer.create(
      <WrappedClass/>
    );
    console.log(tree.toJSON())
  });
});
