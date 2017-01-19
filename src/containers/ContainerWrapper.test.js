import React, { Component } from 'react';
import { AppRegistry, Text } from 'react-native';
import renderer from 'react-test-renderer';

describe('ContainerWrapper', () => {
  let ContainerWrapper;
  let myContainerRef;
  let testParentRef;
  let store;
  const containerName = 'example.MyContainer';

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
      this.state = { propsFromState: {} };
    }

    render() {
      const Child = this.ChildClass;
      return (
        <Child containerId="container1" {...this.state.propsFromState} />
      );
    }
  }

  beforeEach(() => {
    ContainerWrapper = require('./ContainerWrapper').default;
    const Store = require('./Store').default;
    store = new Store();
  });

  it('must have containerId as prop', () => {
    const NavigationContainer = ContainerWrapper.wrap(containerName, MyContainer, store);
    expect(() => {
      renderer.create(<NavigationContainer />);
    }).toThrow(new Error('Container example.MyContainer does not have a containerId!'));
  });

  it('wraps the container and saves to store', () => {
    const NavigationContainer = ContainerWrapper.wrap(containerName, MyContainer, store);
    expect(NavigationContainer).not.toBeInstanceOf(MyContainer);
    const tree = renderer.create(<NavigationContainer containerId={'container1'} />);
    expect(tree.toJSON().children).toEqual(['Hello, World!']);
    expect(myContainerRef).toBeInstanceOf(MyContainer);
  });

  it('injects props from wrapper into original container', () => {
    const NavigationContainer = ContainerWrapper.wrap(containerName, MyContainer, store);
    renderer.create(<NavigationContainer containerId={'container1'} myProp={'yo'} />);
    expect(myContainerRef.props.myProp).toEqual('yo');
  });

  it('updates props from wrapper into original container', () => {
    const NavigationContainer = ContainerWrapper.wrap(containerName, MyContainer, store);
    renderer.create(<TestParent ChildClass={NavigationContainer} />);
    expect(myContainerRef.props.foo).toEqual(undefined);
    testParentRef.setState({ propsFromState: { foo: 'yo' } });
    expect(myContainerRef.props.foo).toEqual('yo');
  });

  it('pulls props from the store and injects them into the inner container', () => {
    store.setPropsForContainerId('container123', { numberProp: 1, stringProp: 'hello', objectProp: { a: 2 } });
    const NavigationContainer = ContainerWrapper.wrap(containerName, MyContainer, store);
    renderer.create(<NavigationContainer containerId={'container123'} />);
    expect(myContainerRef.props).toEqual({ containerId: 'container123', numberProp: 1, stringProp: 'hello', objectProp: { a: 2 } });
  });

  it('updates props from store into inner container', () => {
    const NavigationContainer = ContainerWrapper.wrap(containerName, MyContainer, store);
    renderer.create(<TestParent ChildClass={NavigationContainer} />);
    store.setPropsForContainerId('container1', { myProp: 'hello' });
    expect(myContainerRef.props.foo).toEqual(undefined);
    expect(myContainerRef.props.myProp).toEqual(undefined);
    testParentRef.setState({ propsFromState: { foo: 'yo' } });
    expect(myContainerRef.props.foo).toEqual('yo');
    expect(myContainerRef.props.myProp).toEqual('hello');
  });

  it('protects containerId from change', () => {
    const NavigationContainer = ContainerWrapper.wrap(containerName, MyContainer, store);
    renderer.create(<TestParent ChildClass={NavigationContainer} />);
    expect(myContainerRef.props.containerId).toEqual('container1');
    testParentRef.setState({ propsFromState: { containerId: 'ERROR' } });
    expect(myContainerRef.props.containerId).toEqual('container1');
  });
});
