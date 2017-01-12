import React, {Component} from 'react';
import {AppRegistry, Text} from 'react-native';
import renderer from 'react-test-renderer';

describe('ContainerRegistry', () => {
  let uut;
  let store;
  let myContainerRef;
  let testParentRef;

  class MyContainer extends Component {
    constructor(props) {
      super(props);
      myContainerRef = this; //eslint-disable-line
    }

    render() {
      return <Text>{'Hello, World!'}</Text>;
    }
  }

  class TestParent extends Component { //eslint-disable-line
    constructor(props) {
      super(props);
      testParentRef = this; //eslint-disable-line
      this.ChildClass = props.ChildClass;
      this.state = {propsFromState: {}};
    }

    render() {
      const Child = this.ChildClass;
      return (
        <Child containerId="container1" {...this.state.propsFromState}/>
      );
    }
  }

  beforeEach(() => {
    uut = require('./ContainerRegistry');
    store = require('./Store');
  });

  describe('registerContainer', () => {
    beforeEach(() => {
      AppRegistry.registerComponent = jest.fn(AppRegistry.registerComponent);
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

  describe('NavigationContainer wrapping passed container', () => {
    const containerName = 'example.MyContainer';

    beforeEach(() => {
      uut.registerContainer(containerName, () => MyContainer);
    });

    it('must have containerId as prop', () => {
      const NavigationContainer = store.getContainerClass(containerName);
      expect(() => {
        renderer.create(<NavigationContainer/>);
      }).toThrow(new Error('Container example.MyContainer does not have a containerId!'));
    });

    it('wraps the container and saves to store', () => {
      const NavigationContainer = store.getContainerClass(containerName);
      expect(NavigationContainer).not.toBeInstanceOf(MyContainer);
      const tree = renderer.create(<NavigationContainer containerId={'container1'}/>);
      expect(tree.toJSON().children).toEqual(['Hello, World!']);
      expect(myContainerRef).toBeInstanceOf(MyContainer);
    });

    it('injects props from wrapper into original container', () => {
      const NavigationContainer = store.getContainerClass(containerName);
      renderer.create(<NavigationContainer containerId={'container1'} myProp={'yo'}/>);
      expect(myContainerRef.props.myProp).toEqual('yo');
    });

    it('updates props from wrapper into original container', () => {
      const NavigationContainer = store.getContainerClass(containerName);
      renderer.create(<TestParent ChildClass={NavigationContainer}/>);
      expect(myContainerRef.props.foo).toEqual(undefined);
      testParentRef.setState({propsFromState: {foo: 'yo'}});
      expect(myContainerRef.props.foo).toEqual('yo');
    });

    it('pulls props from the PropsStore and injects them into the inner container', () => {
      store.setPropsForContainerId('container123', {numberProp: 1, stringProp: 'hello', objectProp: {a: 2}});
      const NavigationContainer = store.getContainerClass(containerName);
      renderer.create(<NavigationContainer containerId={'container123'}/>);
      expect(myContainerRef.props).toEqual({containerId: 'container123', numberProp: 1, stringProp: 'hello', objectProp: {a: 2}});
    });

    it('updates props from PropsStore into inner container', () => {
      const NavigationContainer = store.getContainerClass(containerName);
      renderer.create(<TestParent ChildClass={NavigationContainer}/>);
      store.setPropsForContainerId('container1', {myProp: 'hello'});
      expect(myContainerRef.props.foo).toEqual(undefined);
      expect(myContainerRef.props.myProp).toEqual(undefined);
      testParentRef.setState({propsFromState: {foo: 'yo'}});
      expect(myContainerRef.props.foo).toEqual('yo');
      expect(myContainerRef.props.myProp).toEqual('hello');
    });

    it('protects containerId from change', () => {
      const NavigationContainer = store.getContainerClass(containerName);
      renderer.create(<TestParent ChildClass={NavigationContainer}/>);
      expect(myContainerRef.props.containerId).toEqual('container1');
      testParentRef.setState({propsFromState: {containerId: 'ERROR'}});
      expect(myContainerRef.props.containerId).toEqual('container1');
    });
  });
});
