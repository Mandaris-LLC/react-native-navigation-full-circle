import React, {Component} from 'react';
import {AppRegistry} from 'react-native';
import * as PropsStore from './PropsStore';
import * as ScreenStore from './ScreenStore';

export function registerScreen(screenKey, getScreenFunc) {
  const OriginalScreen = getScreenFunc();
  const NavigationScreen = wrapScreen(screenKey, OriginalScreen);
  ScreenStore.saveScreenClass(screenKey, NavigationScreen);
  AppRegistry.registerComponent(screenKey, () => NavigationScreen);
}

export const bla = {};

function wrapScreen(screenKey, OriginalScreen) {
  return class extends Component {
    constructor(props) {
      super(props);
      if (!props.screenId) {
        throw new Error(`Screen ${screenKey} does not have a screenId!`);
      }
      this.state = {
        screenId: props.screenId,
        allProps: {...props, ...PropsStore.getPropsForScreenId(props.screenId)}
      };
    }

    componentWillReceiveProps(nextProps) {
      this.setState({
        allProps: {...nextProps, ...PropsStore.getPropsForScreenId(this.state.screenId)}
      });
    }

    render() {
      return (
        <OriginalScreen
          ref={(r) => bla.ref = r}
          {...this.state.allProps}
          screenId={this.state.screenId}
        />
      );
    }
  };
}
