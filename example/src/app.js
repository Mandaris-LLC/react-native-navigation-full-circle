import React, {
  AppRegistry,
  Component,
  View
} from 'react-native';
import { Navigation } from 'react-native-navigation';

// screen related book keeping
import { registerScreens } from './screens';
registerScreens();

AppRegistry.registerComponent('App', () => App);

export default class App extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <View />
    );
  }
}

// this will start our app
Navigation.startTabBasedApp({
  tabs: [
    {
      label: 'One',
      screen: 'example.FirstTabScreen',
      icon: require('../img/one.png'),
      selectedIcon: require('../img/one_selected.png'),
      title: 'Screen One'
    },
    {
      label: 'Two',
      screen: 'example.SecondTabScreen',
      icon: require('../img/two.png'),
      selectedIcon: require('../img/two_selected.png'),
      title: 'Screen Two'
    },
    {
      label: 'Three',
      screen: 'example.ThirdTabScreen',
      icon: require('../img/three.png'),
      selectedIcon: require('../img/three_selected.png'),
      title: 'Screen Three',
      navigatorStyle: {
        navBarBackgroundColor: '#4dbce9',
        navBarTextColor: '#ffff00',
        navBarButtonColor: '#ffffff',
        statusBarTextColorScheme: 'light'
      }
    }
  ],
  // tabsStyle: {
  //   tabBarButtonColor: '#ffff00',
  //   tabBarSelectedButtonColor: '#ff9900',
  //   tabBarBackgroundColor: '#551A8B'
  // },
  drawer: {
    left: {
      screen: 'example.SideMenu'
    }
  }
});
