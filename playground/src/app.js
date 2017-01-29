import Navigation from 'react-native-navigation';

import { registerContainers } from './containers';

export function start() {
  registerContainers();

  Navigation.onAppLaunched(() => {
    Navigation.startApp({
      container: {
        name: 'com.example.WelcomeScreen'
      }
    });
  });
}

