import React, {Component} from 'react';
import {AppRegistry, NativeModules} from 'react-native';

import PropRegistry from './PropRegistry';

const NativeReactModule = NativeModules.NavigationReactModule;

function startApp(activityParams) {
  PropRegistry.save(activityParams.screen.navigationParams.screenInstanceID, activityParams.screen.passProps);
  NativeReactModule.startApp(activityParams);
}

function push(screenParams) {
  PropRegistry.save(screenParams.navigationParams.screenInstanceID, screenParams.passProps);
  NativeReactModule.push(screenParams);
}

function pop(screenParams) {
  NativeReactModule.pop(screenParams);
}

function popToRoot(screenParams) {
  NativeReactModule.popToRoot(screenParams);
}

function newStack(screenParams) {
  PropRegistry.save(screenParams.navigationParams.screenInstanceID, screenParams.passProps);
  NativeReactModule.newStack(screenParams);
}

function toggleTopBarVisible(screenInstanceID, visible, animated) {
  NativeReactModule.setTopBarVisible(screenInstanceID, visible, animated);
}

function toggleBottomTabsVisible(visible, animated) {
  NativeReactModule.setBottomTabsVisible(visible, animated);
}

function setScreenTitleBarTitle(screenInstanceID, title) {
  NativeReactModule.setScreenTitleBarTitle(screenInstanceID, title);
}

function setScreenTitleBarButtons(screenInstanceID, navigatorEventID, rightButtons, leftButton) {
  NativeReactModule.setScreenTitleBarButtons(screenInstanceID, navigatorEventID, rightButtons, leftButton);
}

function showModal(screenParams) {
  PropRegistry.save(screenParams.navigationParams.screenInstanceID, screenParams.passProps);
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
  toggleBottomTabsVisible,
  setScreenTitleBarTitle,
  setScreenTitleBarButtons,
  showModal,
  dismissTopModal,
  dismissAllModals
};
