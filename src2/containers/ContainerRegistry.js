import React, {Component} from 'react';
import {AppRegistry} from 'react-native';

export function registerContainer(containerKey, getContainerFunc) {
  const OriginalContainer = getContainerFunc();
  const NavigationContainer = wrapContainer(OriginalContainer);
  AppRegistry.registerComponent(containerKey, () => NavigationContainer);
}

function wrapContainer(OriginalContainer) {
  return class extends Component {
    constructor(props) {
      super(props);
      this.state = {
        allProps: {...props}
      };
    }

    componentWillReceiveProps(nextProps) {
      this.setState({
        allProps: {...nextProps}
      });
    }

    render() {
      return (
        <OriginalContainer {...this.state.allProps}/>
      );
    }
  };
}
