import React, {Component} from 'react';
import {AppRegistry, NativeModules} from 'react-native';
import _ from 'lodash';

import Navigation from './Navigation';

NativeBridge = NativeModules.NavigationReactModule;

const resolveAssetSource = require('react-native/Libraries/Image/resolveAssetSource');

function startApp(activityParams) {
  const screen = activityParams.screen;
  if (!screen.screenId) {
    console.error('startApp(activityParams): screenId property must be supplied');
    return;
  }

  NativeBridge.startApp(activityParams);
}

function push(screenParams) {
  NativeBridge.push(screenParams);
}

function pop(screenParams) {
  NativeBridge.pop(screenParams);
}

function popToRoot(screenParams) {
  NativeBridge.popToRoot(screenParams);
}

function newStack(screenParams) {
  NativeBridge.newStack(screenParams);
}

function toggleTopBarVisible(screenInstanceID, visible, animated) {
  NativeBridge.setTopBarVisible(screenInstanceID, visible, animated);
}

function setScreenTitleBarTitle(screenInstanceID, title) {
  NativeBridge.setScreenTitleBarTitle(screenInstanceID, title);
}

function setScreenTitleBarButtons(screenInstanceID, navigatorEventID, buttons) {
  NativeBridge.setScreenTitleBarButtons(screenInstanceID, navigatorEventID, buttons);
}

function showModal(screenParams) {
  NativeBridge.showModal(screenParams);
}

function dismissTopModal() {
  NativeBridge.dismissTopModal();
}

module.exports = {
  startApp,
  push,
  pop,
  popToRoot,
  newStack,
  toggleTopBarVisible,
  setScreenTitleBarTitle,
  setScreenTitleBarButtons,
  showModal,
  dismissTopModal
};
