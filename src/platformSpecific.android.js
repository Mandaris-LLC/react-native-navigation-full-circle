import React, {Component} from 'react';
import {AppRegistry, NativeModules} from 'react-native';
import _ from 'lodash';

import Navigation from './Navigation';

const NativeReactModule = NativeModules.NavigationReactModule;

const resolveAssetSource = require('react-native/Libraries/Image/resolveAssetSource');

function startApp(activityParams) {
  const screen = activityParams.screen;
  if (!screen.screenId) {
    console.error('startApp(activityParams): screenId property must be supplied');
    return;
  }

  NativeReactModule.startApp(activityParams);
}

function push(screenParams) {
  NativeReactModule.push(screenParams);
}

function pop(screenParams) {
  NativeReactModule.pop(screenParams);
}

function popToRoot(screenParams) {
  NativeReactModule.popToRoot(screenParams);
}

function newStack(screenParams) {
  NativeReactModule.newStack(screenParams);
}

function toggleTopBarVisible(screenInstanceID, visible, animated) {
  NativeReactModule.setTopBarVisible(screenInstanceID, visible, animated);
}

function setScreenTitleBarTitle(screenInstanceID, title) {
  NativeReactModule.setScreenTitleBarTitle(screenInstanceID, title);
}

function setScreenTitleBarButtons(screenInstanceID, navigatorEventID, rightButtons, leftButton) {
  NativeReactModule.setScreenTitleBarButtons(screenInstanceID, navigatorEventID, rightButtons, leftButton);
}

function showModal(screenParams) {
  NativeReactModule.showModal(screenParams);
}

function dismissTopModal() {
  NativeReactModule.dismissTopModal();
}

function dismissAllModals() {
  NativeReactModule.dismissAllModals();
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
  dismissTopModal,
  dismissAllModals
};
