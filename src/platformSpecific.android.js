import Navigation from './Navigation';
import utils from './utils';

import {
  RctActivity
} from 'react-native-navigation';

function startSingleScreenApp(params) {
  let screen = params.screen;
  if (!screen.screen) {
    console.error('startSingleScreenApp(params): screen must include a screen property');
    return;
  }

  console.log(RctActivity);
  addNavigationParams(screen);
  RctActivity.startSingleScreenApp(screen);
}

function startTabBasedApp(params) {
  if (!params.tabs) {
    console.error('startTabBasedApp(params): params.tabs is required');
    return;
  }

  params.tabs.forEach(function (tab, idx) {
    addNavigationParams(tab, idx)
  });

  RctActivity.startTabBasedApp(params.tabs);
}

function navigatorPush(navigator, params) {
  addNavigationParams(params)
  RctActivity.navigatorPush(params);
}

function addNavigationParams(screen, idx = '') {
  screen.stackID = utils.getRandomId();
  screen.navigatorID = utils.getRandomId() + '_nav' + idx;
  screen.screenInstanceID = utils.getRandomId();
  screen.navigatorEventID = screen.screenInstanceID + '_events';
}

export default {
  startSingleScreenApp,
  startTabBasedApp,
  navigatorPush
}
