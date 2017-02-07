import Navigation from 'react-native-navigation';

import WelcomeScreen from './WelcomeScreen';
import SimpleScreen from './SimpleScreen';
import LifecycleScreen from './LifecycleScreen';

export function registerContainers() {
  Navigation.registerContainer(`navigation.playground.WelcomeScreen`, () => WelcomeScreen);
  Navigation.registerContainer(`navigation.playground.SimpleScreen`, () => SimpleScreen);
  Navigation.registerContainer(`navigation.playground.LifecycleScreen`, () => LifecycleScreen);
}
