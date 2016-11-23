import React, {Component} from 'react';
import {AppRegistry} from 'react-native';
import * as PropsStore from './PropsStore';
import * as ContainerStore from './ContainerStore';

export function registerContainer(containerKey, getContainerFunc) {
  const OriginalContainer = getContainerFunc();
  const NavigationContainer = wrapContainer(containerKey, OriginalContainer);
  ContainerStore.saveContainerClass(containerKey, NavigationContainer);
  AppRegistry.registerComponent(containerKey, () => NavigationContainer);
}

export function getRegisteredContainer(containerKey) {
  return ContainerStore.getContainerClass(containerKey);
}

function wrapContainer(containerKey, OriginalContainer) {
  return class extends Component {
    constructor(props) {
      super(props);
      if (!props.screenId) {
        throw new Error(`Screen ${containerKey} does not have a screenId!`);
      }
      this.screenId = props.screenId;
      this.state = {
        allProps: {...props, ...PropsStore.getPropsForScreenId(this.screenId)}
      };
    }

    componentWillReceiveProps(nextProps) {
      this.setState({
        allProps: {...nextProps, ...PropsStore.getPropsForScreenId(this.screenId)}
      });
    }

    render() {
      return (
        <OriginalContainer {...this.state.allProps} screenId={this.screenId}/>
      );
    }
  };
}
