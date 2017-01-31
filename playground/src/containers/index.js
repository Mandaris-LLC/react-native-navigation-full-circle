import Navigation from 'react-native-navigation';

import WelcomeScreen from './WelcomeScreen';
import SimpleScreen from './SimpleScreen';

export function registerContainers() {
  Navigation.registerContainer(`com.example.WelcomeScreen`, () => WelcomeScreen);
  Navigation.registerContainer(`com.example.SimpleScreen`, () => SimpleScreen);
}
