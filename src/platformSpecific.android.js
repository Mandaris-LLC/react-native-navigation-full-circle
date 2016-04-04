import Navigation from './Navigation';

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
}

function startTabBasedApp(params) {
  if (!params.tabs) {
    console.error('startTabBasedApp(params): params.tabs is required');
    return;
  }

  RctActivity.startTabBasedApp(params.tabs);
}

export default {
  startSingleScreenApp,
  startTabBasedApp
}
