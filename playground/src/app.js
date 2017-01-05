import Navigation from 'react-native-navigation';

export function start() {
  Navigation.startApp({
    container: {
      key: 'com.example.WelcomeScreen'
    }
  });
}

