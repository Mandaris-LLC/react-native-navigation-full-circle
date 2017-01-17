import React, {Component} from 'react';
import {AppRegistry} from 'react-native';

import * as Store from './Store';

export function registerContainer(containerName, getContainerFunc) {
  const OriginalContainer = getContainerFunc();
  const NavigationContainer = wrapContainer(containerName, OriginalContainer);
  Store.setContainerClass(containerName, NavigationContainer);
  AppRegistry.registerComponent(containerName, () => NavigationContainer);
}

function wrapContainer(containerName, OriginalContainer) {
  return class extends Component {
    constructor(props) {
      super(props);
      if (!props.containerId) {
        throw new Error(`Container ${containerName} does not have a containerId!`);
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
