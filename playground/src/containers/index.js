const Navigation = require('react-native-navigation');
const WelcomeScreen = require('./WelcomeScreen');
const TextScreen = require('./TextScreen');
const PushedScreen = require('./PushedScreen');
const LifecycleScreen = require('./LifecycleScreen');
const ModalScreen = require('./ModalScreen');
const OptionsScreen = require('./OptionsScreen');
const OrientationScreen = require('./OrientationScreen');
const OrientationMenuScreen = require('./OrientationMenuScreen');
const ScrollViewScreen = require('./ScrollViewScreen');

function registerContainers() {
  Navigation.registerContainer(`navigation.playground.ScrollViewScreen`, () => ScrollViewScreen);
  Navigation.registerContainer(`navigation.playground.WelcomeScreen`, () => WelcomeScreen);
  Navigation.registerContainer(`navigation.playground.ModalScreen`, () => ModalScreen);
  Navigation.registerContainer(`navigation.playground.LifecycleScreen`, () => LifecycleScreen);
  Navigation.registerContainer(`navigation.playground.TextScreen`, () => TextScreen);
  Navigation.registerContainer(`navigation.playground.PushedScreen`, () => PushedScreen);
  Navigation.registerContainer(`navigation.playground.OptionsScreen`, () => OptionsScreen);
  Navigation.registerContainer(`navigation.playground.OrientationScreen`, () => OrientationScreen);
  Navigation.registerContainer(`navigation.playground.OrientationMenuScreen`, () => OrientationMenuScreen);
}

module.exports = {
  registerContainers
};
