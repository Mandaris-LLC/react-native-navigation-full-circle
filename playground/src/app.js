const Navigation = require('react-native-navigation');
const { registerContainers } = require('./containers');

function start() {
  registerContainers();
  Navigation.events().onAppLaunched(() => {
    Navigation.setRoot({
      stack: {
        children: [
          {
            component: {
              name: 'navigation.playground.WelcomeScreen'
            }
          }
        ]
      }
    });
  });
}

module.exports = {
  start
};
