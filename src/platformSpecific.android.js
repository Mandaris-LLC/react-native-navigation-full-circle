import React, {Component} from 'react';
import {AppRegistry, NativeModules} from 'react-native';
import _ from 'lodash';

import Navigation from './Navigation';

const resolveAssetSource = require('react-native/Libraries/Image/resolveAssetSource');

function startApp(activityParams) {
  const screen = activityParams.screen;
  if (!screen.screenId) {
    console.error('startApp(activityParams): screenId property must be supplied');
    return;
  }

  NativeModules.NavigationReactModule.startApp(activityParams);
}

function push(screenParams) {
  NativeModules.NavigationReactModule.push(screenParams);
}

function pop(screenParams) {
  NativeModules.NavigationReactModule.pop(screenParams);
}

module.exports = {
  startApp,
  push,
  pop
};
