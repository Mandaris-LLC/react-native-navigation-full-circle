import { AppRegistry } from 'react-native';
import platformSpecific from './platformSpecific';

const registeredScreens = {};

function registerScreen(screenID, generator) {
  registeredScreens[screenID] = generator;
  AppRegistry.registerComponent(screenID, generator);
}

function getRegisteredScreen(screenID) {
  const generator = registeredScreens[screenID];
  if (!generator) {
    console.error('Navigation.getRegisteredScreen: ' + screenID + ' used but not yet registered');
    return;
  }
  return generator();
}

function showModal(params = {}) {
  return platformSpecific.showModal(params);
}

function dismissModal(params = {}) {
  return platformSpecific.dismissModal(params);
}

export default Navigation = {
  registerScreen,
  getRegisteredScreen,
  showModal,
  dismissModal,
  startTabBasedApp: platformSpecific.startTabBasedApp,
  startSingleScreenApp: platformSpecific.startSingleScreenApp
}
