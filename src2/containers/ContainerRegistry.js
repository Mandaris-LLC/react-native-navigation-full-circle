import React, {Component} from 'react';
import {AppRegistry} from 'react-native';

export function registerContainer(containerKey, getContainerFunc) {
  const OriginalContainer = getContainerFunc();
  const WrappedContainer = wrapContainer(OriginalContainer);
  AppRegistry.registerComponent(containerKey, () => WrappedContainer);
}

function wrapContainer(OriginalContainer) {
  return class extends Component {
    constructor(props) {
      super(props);
    }

    render() {
      return (
        <OriginalContainer/>
      );
    }
  };
}
