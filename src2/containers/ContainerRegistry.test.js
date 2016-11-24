import {AppRegistry, Text} from 'react-native';
import React, {Component} from 'react';
import renderer from 'react-test-renderer';

describe('ComponentRegistry', () => {
  let uut;
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
        <Child screenId="screen1" {...this.state.propsFromState}/>
      );
    }
  }

  beforeEach(() => {
    uut = require('./ContainerRegistry');
  });

  afterEach(() => {
    myContainerRef = null;
    testParentRef = null;
  });

  describe('registerContainer', () => {
    it('registers container component by containerKey into AppRegistry', () => {
      AppRegistry.registerComponent = jest.fn(AppRegistry.registerComponent);
      expect(AppRegistry.registerComponent).not.toHaveBeenCalled();
      uut.registerContainer('example.MyContainer.key', () => MyContainer);
      expect(AppRegistry.registerComponent).toHaveBeenCalledTimes(1);
      expect(AppRegistry.registerComponent.mock.calls[0][0]).toEqual('example.MyContainer.key');
    });

    it('resulting in a normal component', () => {
      AppRegistry.registerComponent = jest.fn(AppRegistry.registerComponent);
      uut.registerContainer('example.MyContainer.key', () => MyContainer);
      const Container = AppRegistry.registerComponent.mock.calls[0][1]();
      const tree = renderer.create(<Container screenId="123"/>);
      expect(tree.toJSON().children).toEqual(['Hello, World!']);
    });
  });

  describe('wrapping NavigationContainer', () => {
    const containerKey = 'example.MyContainer';

    beforeEach(() => {
      uut.registerContainer(containerKey, () => MyContainer);
    });

    it('asserts has screenId as prop', () => {
      const NavigationContainer = uut.getRegisteredContainer(containerKey);
      expect(() => {
        renderer.create(<NavigationContainer/>);
      }).toThrow(new Error('Screen example.MyContainer does not have a screenId!'));
    });

    it('wraps the container and saves to store', () => {
      const NavigationContainer = uut.getRegisteredContainer(containerKey);
      const tree = renderer.create(<NavigationContainer screenId={'screen1'}/>);
      expect(tree.toJSON().children).toEqual(['Hello, World!']);
      expect(myContainerRef).toBeInstanceOf(MyContainer);
    });

    it('injects props from wrapper into original container', () => {
      const NavigationContainer = uut.getRegisteredContainer(containerKey);
      renderer.create(<NavigationContainer screenId={'screen1'} myProp={'yo'}/>);
      expect(myContainerRef.props.myProp).toEqual('yo');
    });

    it('updates props from wrapper into original container', () => {
      const NavigationContainer = uut.getRegisteredContainer(containerKey);
      renderer.create(<TestParent ChildClass={NavigationContainer}/>);
      expect(myContainerRef.props.foo).toEqual(undefined);
      testParentRef.setState({propsFromState: {foo: 'yo'}});
      expect(myContainerRef.props.foo).toEqual('yo');
    });

    it('pulls props from the PropsStore and injects them into the inner container', () => {
      require('./PropsStore').setPropsForScreenId('screen123', {numberProp: 1, stringProp: 'hello', objectProp: {a: 2}});
      const NavigationContainer = uut.getRegisteredContainer(containerKey);
      renderer.create(<NavigationContainer screenId={'screen123'}/>);
      expect(myContainerRef.props).toEqual({screenId: 'screen123', numberProp: 1, stringProp: 'hello', objectProp: {a: 2}});
    });

    it('updates props from PropsStore into inner container', () => {
      const NavigationContainer = uut.getRegisteredContainer(containerKey);
      renderer.create(<TestParent ChildClass={NavigationContainer}/>);
      require('./PropsStore').setPropsForScreenId('screen1', {myProp: 'hello'});
      expect(myContainerRef.props.foo).toEqual(undefined);
      expect(myContainerRef.props.myProp).toEqual(undefined);
      testParentRef.setState({propsFromState: {foo: 'yo'}});
      expect(myContainerRef.props.foo).toEqual('yo');
      expect(myContainerRef.props.myProp).toEqual('hello');
    });

    it('protects screenId from change', () => {
      const NavigationContainer = uut.getRegisteredContainer(containerKey);
      renderer.create(<TestParent ChildClass={NavigationContainer}/>);
      expect(myContainerRef.props.screenId).toEqual('screen1');
      testParentRef.setState({propsFromState: {screenId: 'ERROR'}});
      expect(myContainerRef.props.screenId).toEqual('screen1');
    });
  });
});
