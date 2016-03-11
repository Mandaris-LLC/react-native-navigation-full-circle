import { Navigation } from 'react-native-navigation';

import './screens/FirstTabScreen';
import './screens/SecondTabScreen';
import './screens/ThirdTabScreen';

Navigation.startTabBasedApp({
  tabs: [
    {
      title: 'One',
      screen: 'example.FirstTabScreen',
      icon: require('../img/one.png'),
      selectedIcon: require('../img/one_selected.png'),
      screenTitle: 'Screen One'
    },
    {
      title: 'Two',
      screen: 'example.SecondTabScreen',
      icon: require('../img/two.png'),
      selectedIcon: require('../img/two_selected.png'),
      screenTitle: 'Screen Two'
    },
    {
      title: 'Three',
      screen: 'example.ThirdTabScreen',
      icon: require('../img/three.png'),
      selectedIcon: require('../img/three_selected.png'),
      screenTitle: 'Screen Three',
      navigatorStyle: {
        navBarBackgroundColor: '#26ade4',
        navBarTextColor: '#f0f0f0',
        navBarButtonColor: '#ffffff'
      }
    }
  ]
});
