import React, {Component} from 'react';
import {AppRegistry, Text} from 'react-native';
import renderer from 'react-test-renderer';

describe('ComponentRegistry', () => {
  let uut;
  let myScreenRef;
  let testParentRef;
  let screenStore;

  class MyScreen extends Component {
    constructor(props) {
      super(props);
      myScreenRef = this; //eslint-disable-line
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
    uut = require('./ScreenRegistry');
    screenStore = require('./ScreenStore');
  });

  afterEach(() => {
    myScreenRef = null;
    testParentRef = null;
  });

  describe('registerScreen', () => {
    beforeEach(() => {
      AppRegistry.registerComponent = jest.fn(AppRegistry.registerComponent);
    });

    it('registers screen component by screenKey into AppRegistry', () => {
      expect(AppRegistry.registerComponent).not.toHaveBeenCalled();
      uut.registerScreen('example.MyScreen.key', () => MyScreen);
      expect(AppRegistry.registerComponent).toHaveBeenCalledTimes(1);
      expect(AppRegistry.registerComponent.mock.calls[0][0]).toEqual('example.MyScreen.key');
    });

    it('resulting in a normal component', () => {
      uut.registerScreen('example.MyScreen.key', () => MyScreen);
      const Screen = AppRegistry.registerComponent.mock.calls[0][1]();
      const tree = renderer.create(<Screen screenId="123"/>);
      expect(tree.toJSON().children).toEqual(['Hello, World!']);
    });
  });

  describe('NavigationScreen wrapping', () => {
    const screenKey = 'example.MyScreen';

    beforeEach(() => {
      uut.registerScreen(screenKey, () => MyScreen);
    });

    it('must have screenId as prop', () => {
      const NavigationScreen = screenStore.getScreenClass(screenKey);
      expect(() => {
        renderer.create(<NavigationScreen/>);
      }).toThrow(new Error('Screen example.MyScreen does not have a screenId!'));
    });

    it('wraps the screen and saves to store', () => {
      const NavigationScreen = screenStore.getScreenClass(screenKey);
      expect(NavigationScreen).not.toBeInstanceOf(MyScreen);
      const tree = renderer.create(<NavigationScreen screenId={'screen1'}/>);
      expect(tree.toJSON().children).toEqual(['Hello, World!']);
      expect(myScreenRef).toBeInstanceOf(MyScreen);
    });

    it('injects props from wrapper into original screen', () => {
      const NavigationScreen = screenStore.getScreenClass(screenKey);
      renderer.create(<NavigationScreen screenId={'screen1'} myProp={'yo'}/>);
      expect(myScreenRef.props.myProp).toEqual('yo');
    });

    it('updates props from wrapper into original screen', () => {
      const NavigationScreen = screenStore.getScreenClass(screenKey);
      renderer.create(<TestParent ChildClass={NavigationScreen}/>);
      expect(myScreenRef.props.foo).toEqual(undefined);
      testParentRef.setState({propsFromState: {foo: 'yo'}});
      expect(myScreenRef.props.foo).toEqual('yo');
    });

    it('pulls props from the PropsStore and injects them into the inner screen', () => {
      require('./PropsStore').setPropsForScreenId('screen123', {numberProp: 1, stringProp: 'hello', objectProp: {a: 2}});
      const NavigationScreen = screenStore.getScreenClass(screenKey);
      renderer.create(<NavigationScreen screenId={'screen123'}/>);
      expect(myScreenRef.props).toEqual({screenId: 'screen123', numberProp: 1, stringProp: 'hello', objectProp: {a: 2}});
    });

    it('updates props from PropsStore into inner screen', () => {
      const NavigationScreen = screenStore.getScreenClass(screenKey);
      renderer.create(<TestParent ChildClass={NavigationScreen}/>);
      require('./PropsStore').setPropsForScreenId('screen1', {myProp: 'hello'});
      expect(myScreenRef.props.foo).toEqual(undefined);
      expect(myScreenRef.props.myProp).toEqual(undefined);
      testParentRef.setState({propsFromState: {foo: 'yo'}});
      expect(myScreenRef.props.foo).toEqual('yo');
      expect(myScreenRef.props.myProp).toEqual('hello');
    });

    it('protects screenId from change', () => {
      const NavigationScreen = screenStore.getScreenClass(screenKey);
      renderer.create(<TestParent ChildClass={NavigationScreen}/>);
      expect(myScreenRef.props.screenId).toEqual('screen1');
      testParentRef.setState({propsFromState: {screenId: 'ERROR'}});
      expect(myScreenRef.props.screenId).toEqual('screen1');
    });
  });
});
