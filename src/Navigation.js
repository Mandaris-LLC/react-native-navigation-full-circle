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

export default Navigation = {
  registerScreen,
  getRegisteredScreen,
  startTabBasedApp: platformSpecific.startTabBasedApp,
  startSingleScreenApp: platformSpecific.startSingleScreenApp
}
