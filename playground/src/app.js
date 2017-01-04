import {
  Platform
} from 'react-native';
import {Navigation} from 'react-native-navigation';

// screen related book keeping
import {registerScreens} from './screens';
registerScreens();

const createTabs = () => {
  let tabs = [
    {
      label: 'One',
      screen: 'com.example.FirstTabScreen',
      icon: require('../img/one.png'),
      selectedIcon: require('../img/one_selected.png'),
      title: 'Screen One',
      navigatorStyle: {
          tabBarHidden: true
      }
    },
    {
      label: 'Two',
      screen: 'com.example.SecondTabScreen',
      icon: require('../img/two.png'),
      selectedIcon: require('../img/two_selected.png'),
      title: 'Screen Two',
      navigatorStyle: {
        tabBarBackgroundColor: '#4dbce9',
      }
    }
  ];
  if (Platform.OS === 'android') {
    tabs.push({
      label: 'Collapsing',
      screen: 'example.CollapsingTopBarScreen',
      icon: require('../img/one.png'),
      title: 'Collapsing',
    });
  }
  return tabs;
};

import * as Commands from './../node_modules/react-native-navigation/src2/commands/valid-commands';
//Navigation.startApp(Commands.singleScreenApp);
//Navigation.startApp(Commands.tabBasedApp);
//Navigation.startApp(Commands.singleWithSideMenu);
//Navigation.startApp(Commands.singleWithRightSideMenu);
//Navigation.startApp(Commands.singleWithBothMenus);
//Navigation.startApp(Commands.tabBasedWithSideMenu);

// this will start our app
Navigation.startTabBasedApp({
  tabs: createTabs(),
  appStyle: {
    tabBarBackgroundColor: '#0f2362',
    tabBarButtonColor: '#ffffff',
    tabBarSelectedButtonColor: '#63d7cc',
  },
  drawer: {
    left: {
      screen: 'com.example.SideMenu'
    }
  }
});
//Navigation.startSingleScreenApp({
//  screen: {
//    screen: 'com.example.FirstTabScreen',
//    title: 'Navigation',
//    navigatorStyle: {
//      navBarBackgroundColor: '#4dbce9',
//      navBarTextColor: '#ffff00',
//      navBarSubtitleTextColor: '#ff0000',
//      navBarButtonColor: '#ffffff',
//      statusBarTextColorScheme: 'light',
//      tabBarHidden: true
//    }
//  },
//  drawer: {
//    left: {
//      screen: 'com.example.SideMenu'
//    }
//  }
//});

