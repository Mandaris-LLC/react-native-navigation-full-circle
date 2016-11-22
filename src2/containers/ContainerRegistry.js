import React, {Component} from 'react';
import {AppRegistry} from 'react-native';

export function registerContainer(containerKey, getContainerFunc) {
  const OriginalContainer = getContainerFunc();
  const NavigationContainer = wrapContainer(containerKey, OriginalContainer);
  AppRegistry.registerComponent(containerKey, () => NavigationContainer);
}

function wrapContainer(containerKey, OriginalContainer) {
  return class extends Component {
    constructor(props) {
      super(props);
      if (!props.screenId) {
        throw new Error(`Screen ${containerKey} does not have a screenId!`);
      }
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
