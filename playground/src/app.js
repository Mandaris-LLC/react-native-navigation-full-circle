const Navigation = require('react-native-navigation');
const { registerComponents } = require('./components');

function start() {
  registerComponents();
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
