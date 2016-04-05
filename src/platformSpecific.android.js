import Navigation from './Navigation';
import utils from './utils';

import {
  RctActivity
} from 'react-native-controllers';

function startSingleScreenApp(params) {
  const screen = params.screen;
  if (!screen.screen) {
    console.error('startSingleScreenApp(params): screen must include a screen property');
    return;
  }

  console.warn('startSingleScreenApp not implemented yet');
  // RctActivity.startSingleScreenApp(params);
  // add screenInstanceID using random

}

function startTabBasedApp(params) {
  if (!params.tabs) {
    console.error('startTabBasedApp(params): params.tabs is required');
    return;
  }

  // Add stackID for each tab
  // TODO: Add propper documentation + switch to UUID? -guy
  params.tabs.forEach(function (tab, idx) {
    tab.stackID = utils.getRandomId();
    tab.navigatorID = utils.getRandomId() + '_nav' + idx;
    tab.screenInstanceID = utils.getRandomId();
    tab.navigatorEventID = tab.screenInstanceID + '_events';
  });

  RctActivity.startTabBasedApp(params.tabs);
}

function navigatorPush(navigator, params) {
  RctActivity.navigatorPush(params);
}

export default {
  startSingleScreenApp,
  startTabBasedApp,
  navigatorPush
}
