import React, {
  AppRegistry,
  Component
} from 'react-native';

import Navigation from './Navigation';
import utils from './utils';

import {
  RctActivity
} from 'react-native-navigation';

var resolveAssetSource = require('resolveAssetSource');

function startSingleScreenApp(params) {
  let screen = params.screen;
  if (!screen.screen) {
    console.error('startSingleScreenApp(params): screen must include a screen property');
    return;
  }

  addNavigatorParams(screen);
  addNavigatorButtons(screen);
  RctActivity.startSingleScreenApp(screen);
}

function startTabBasedApp(params) {
  if (!params.tabs) {
    console.error('startTabBasedApp(params): params.tabs is required');
    return;
  }

  params.tabs.forEach(function (tab, idx) {
    addNavigatorParams(tab, null, idx)
    addNavigatorButtons(tab);
  });

  RctActivity.startTabBasedApp(params.tabs);
}

function navigatorPush(navigator, params) {
  addNavigatorParams(params, navigator)
  addNavigatorButtons(params);
  RctActivity.navigatorPush(params);
}

function navigatorPop(navigator, params) {
  RctActivity.navigatorPop(navigator);
}

function addNavigatorParams(screen, navigator = null, idx = '') {
  screen.navigatorID = navigator ? navigator.navigatorID : utils.getRandomId() + '_nav' + idx;
  screen.screenInstanceID = utils.getRandomId();
  screen.navigatorEventID = navigator ? navigator.navigatorEventID : screen.screenInstanceID + '_events';
}

function addNavigatorButtons(screen) {
  const Screen = Navigation.getRegisteredScreen(screen.screen);
  Object.assign(screen, Screen.navigatorButtons);

  // Get image uri from image id
  if (screen.rightButtons) {
    screen.rightButtons.forEach(function(button) {
      if (button.icon) {
        const icon = resolveAssetSource(button.icon);
        if (icon) {
          button.icon = icon.uri;
        }
      }
    });
  }
}

export default {
  startSingleScreenApp,
  startTabBasedApp,
  navigatorPush,
  navigatorPop
}
