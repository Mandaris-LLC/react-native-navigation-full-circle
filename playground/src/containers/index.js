import Navigation from 'react-native-navigation';

import WelcomeScreen from './WelcomeScreen';
import SimpleTabScreen from './SimpleTabScreen';

export function registerContainers() {
  Navigation.registerContainer(`com.example.WelcomeScreen`, () => WelcomeScreen);
  Navigation.registerContainer(`com.example.SimpleTabScreen`, () => SimpleTabScreen);
}
