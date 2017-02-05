import Navigation from 'react-native-navigation';

import WelcomeScreen from './WelcomeScreen';
import SimpleScreen from './SimpleScreen';
import LifecycleScreen from './LifecycleScreen';

export function registerContainers() {
  Navigation.registerContainer(`com.example.WelcomeScreen`, () => WelcomeScreen);
  Navigation.registerContainer(`com.example.SimpleScreen`, () => SimpleScreen);
  Navigation.registerContainer(`com.example.LifecycleScreen`, () => LifecycleScreen);
}
