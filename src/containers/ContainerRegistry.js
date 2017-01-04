import React, {Component} from 'react';
import {AppRegistry} from 'react-native';
import * as PropsStore from './PropsStore';
import * as ContainerStore from './ContainerStore';

export function registerContainer(containerKey, getContainerFunc) {
  const OriginalContainer = getContainerFunc();
  const NavigationContainer = wrapContainer(containerKey, OriginalContainer);
  ContainerStore.setContainerClass(containerKey, NavigationContainer);
  AppRegistry.registerComponent(containerKey, () => NavigationContainer);
}

export const bla = {};

function wrapContainer(containerKey, OriginalContainer) {
  return class extends Component {
    constructor(props) {
      super(props);
      if (!props.containerId) {
        throw new Error(`Container ${containerKey} does not have a containerId!`);
      }
      this.state = {
        containerId: props.containerId,
        allProps: {...props, ...PropsStore.getPropsForContainerId(props.containerId)}
      };
    }

    componentWillReceiveProps(nextProps) {
      this.setState({
        allProps: {...nextProps, ...PropsStore.getPropsForContainerId(this.state.containerId)}
      });
    }

    render() {
      return (
        <OriginalContainer
          ref={(r) => bla.ref = r}
          {...this.state.allProps}
          containerId={this.state.containerId}
        />
      );
    }
  };
}
