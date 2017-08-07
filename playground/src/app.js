import Navigation from 'react-native-navigation';

import { registerContainers } from './containers';

function start() {
  registerContainers();

  Navigation.events().onAppLaunched(() => {
    Navigation.setRoot({
      container: {
        name: 'navigation.playground.WelcomeScreen'
      }
    });
  });
}

module.exports = {
  start
};
