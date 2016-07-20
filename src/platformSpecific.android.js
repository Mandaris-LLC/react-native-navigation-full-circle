import React, {Component} from 'react';
import {AppRegistry, NativeModules} from 'react-native';
import _ from 'lodash';

import Navigation from './Navigation';

const resolveAssetSource = require('react-native/Libraries/Image/resolveAssetSource');

function startApp(params) {
  const screen = params.screen;
  if (!screen.screenId) {
    console.error('startApp(params): screenId property must be supplied');
    return;
  }

  const actualScreen = Navigation.getRegisteredScreen(screen.screenId);
  screen.screenInstanceId = _.uniqueId('screenInstanceId');

  NativeModules.NavigationReactModule.startApp(params);
}

module.exports = {
  startApp
};
