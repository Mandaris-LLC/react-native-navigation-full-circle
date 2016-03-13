import { Navigation } from 'react-native-navigation';

import './screens/FirstTabScreen';
import './screens/SecondTabScreen';
import './screens/ThirdTabScreen';

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
        navBarTextColor: '#f7f7f7',
        navBarButtonColor: '#ffffff'
      }
    }
  ]
});
