import React, {Component} from 'react';
import {AppRegistry} from 'react-native';
import * as Store from './Store';

export function registerContainer(containerKey, getContainerFunc) {
  const OriginalContainer = getContainerFunc();
  const NavigationContainer = wrapContainer(containerKey, OriginalContainer);
  Store.setContainerClass(containerKey, NavigationContainer);
  AppRegistry.registerComponent(containerKey, () => NavigationContainer);
}

function wrapContainer(containerKey, OriginalContainer) {
  return class extends Component {
    constructor(props) {
      super(props);
      if (!props.containerId) {
        //throw new Error(`Container ${containerKey} does not have a containerId!`);
      }
      this.state = {
        containerId: props.containerId,
        allProps: {...props, ...Store.getPropsForContainerId(props.containerId)}
      };
    }

    componentWillReceiveProps(nextProps) {
      this.setState({
        allProps: {...nextProps, ...Store.getPropsForContainerId(this.state.containerId)}
      });
    }

    render() {
      return (
        <OriginalContainer
          {...this.state.allProps}
          containerId={this.state.containerId}
        />
      );
    }
  };
}
