import {Navigation} from 'react-native-navigation';

// screen related book keeping
import {registerScreens} from './screens';
registerScreens();

// this will start our app
Navigation.startSingleScreenApp({
  screen: {
    screen: 'example.FirstTabScreen',
    title: 'Login',
    navigatorStyle: {
      navBarBackgroundColor: '#4dbce9',
      navBarTextColor: '#ffff00',
      navBarSubtitleTextColor: '#ff0000',
      navBarButtonColor: '#ffffff',
      statusBarTextColorScheme: 'light'
    }
  },
  drawer: {
    left: {
      screen: 'example.SideMenu'
    }
  }
});
