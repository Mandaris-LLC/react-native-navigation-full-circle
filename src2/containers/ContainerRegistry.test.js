import _ from 'lodash';
import {AppRegistry, Text} from 'react-native';
import React, {Component} from 'react';
import renderer from 'react-test-renderer';

class MyContainer extends Component {
  render() {
    const txt = `Hello, ${_.get(this.props, 'name', 'World')}!`;
    return (
      <Text>{txt}</Text>
    );
  }
}

describe('ComponentRegistry', () => {
  let uut;

  beforeEach(() => {
    AppRegistry.registerComponent = jest.fn(AppRegistry.registerComponent);
    uut = require('./ContainerRegistry');
  });

  function getRegisteredComponentClassFromAppRegistry() {
    return AppRegistry.registerComponent.mock.calls[0][1]();
  }

  it('registers container component by containerKey into AppRegistry', () => {
    expect(AppRegistry.registerComponent).not.toHaveBeenCalled();

    uut.registerContainer('example.MyContainer.key', () => MyContainer);

    expect(AppRegistry.registerComponent).toHaveBeenCalledTimes(1);
    expect(AppRegistry.registerComponent.mock.calls[0][0]).toEqual('example.MyContainer.key');
  });

  it('wraps the container', () => {
    uut.registerContainer('example.MyContainer', () => MyContainer);

    const NavigationContainer = getRegisteredComponentClassFromAppRegistry();
    const tree = renderer.create(
      <NavigationContainer/>
    );
    expect(tree.toJSON().children).toEqual(['Hello, World!']);
  });

  it('passes props from wrapper into original container', () => {
    uut.registerContainer('example.MyContainer', () => MyContainer);

    const NavigationContainer = getRegisteredComponentClassFromAppRegistry();
    const tree = renderer.create(
      <NavigationContainer name="Daniel"/>
    );
    expect(tree.toJSON().children).toEqual(['Hello, Daniel!']);
  });
  //
  //xit('updates props into original container', () => {
  //uut.registerContainer('example.MyContainer', () => MyContainer);
  //
  //const NavigationContainer = getRegisteredComponentClassFromAppRegistry();
  //
  //class TestParent extends Component {
  //  constructor(props) {
  //    super(props);
  //    this.state = {};
  //  }
  //
  //  render() {
  //    return (
  //      <NavigationContainer/>
  //    );
  //  }
  //}
  //
  //let navContainerRef;
  //const NavigationContainer = getRegisteredComponentClassFromAppRegistry();
  //const tree = renderer.create(
  //  <NavigationContainer ref={(r) => navContainerRef = r} name={_.get(navContainerRef, 'state.name')}/>
  //);
  //expect(tree.toJSON().children).toEqual(['Hello, World!']);
  //navContainerRef.setState({name: 'Gandalf'});
  //expect(tree.toJSON().children).toEqual(['Hello, Gandalf!']);
  //});
});
