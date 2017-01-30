import Navigation from 'react-native-navigation';

import { registerContainers } from './containers';

export function start() {
  registerContainers();

  Navigation.events().onAppLaunched(() => {
    Navigation.setRoot({
      container: {
        name: 'com.example.WelcomeScreen'
      }
    });
  });
}

