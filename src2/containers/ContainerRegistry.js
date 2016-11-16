import React from 'react';
import {AppRegistry, Component} from 'react-native';

export function registerContainer(containerKey, getContainerFunc) {
  const OrigContainer = getContainerFunc();
  const WrappedContainer = wrapContainer(OrigContainer);
  AppRegistry.registerComponent(containerKey, () => WrappedContainer);
}

function wrapContainer(OrigComponent) {
  return class extends Component {
    //constructor(props) {
    //  super(props);
    //}

    render() {
      return (
        <OrigComponent/>
      );
    }
  };
}
