import Navigation from 'react-native-navigation';

import WelcomeScreen from './WelcomeScreen';

export function registerContainers() {
  Navigation.registerContainer(`com.example.WelcomeScreen`, () => WelcomeScreen);
}
