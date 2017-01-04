import {Navigation} from 'react-native-navigation';

import FirstTabScreen from './FirstTabScreen';
import SecondTabScreen from './SecondTabScreen';
import PushedScreen from './PushedScreen';
import StyledScreen from './StyledScreen';
import SideMenu from './SideMenu';
import ModalScreen from './ModalScreen';
import NotificationScreen from './NotificationScreen';
import LightBoxScreen from './LightBoxScreen';

// register all screens of the app (including internal ones)
export function registerScreens() {
  Navigation.registerComponent('com.example.FirstTabScreen', () => FirstTabScreen);
  Navigation.registerComponent('com.example.SecondTabScreen', () => SecondTabScreen);
  Navigation.registerComponent('com.example.PushedScreen', () => PushedScreen);
  Navigation.registerComponent('com.example.StyledScreen', () => StyledScreen);
  Navigation.registerComponent('com.example.ModalScreen', () => ModalScreen);
  Navigation.registerComponent('com.example.NotificationScreen', () => NotificationScreen);
  Navigation.registerComponent('com.example.SideMenu', () => SideMenu);
  Navigation.registerComponent('com.example.LightBoxScreen', () => LightBoxScreen);
	
  Navigation.registerComponent('com.example.MyScreen', () => FirstTabScreen);
  Navigation.registerComponent('com.example.Menu', () => SideMenu);
  Navigation.registerComponent('com.example.Menu1', () => SideMenu);
  Navigation.registerComponent('com.example.Menu2', () => SideMenu);
	
}
