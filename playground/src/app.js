import React, {Component} from 'react';
import {View, Text} from 'react-native';

import Navigation from 'react-native-navigation';

const styles = {
  root: {
    flexGrow: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#f5fcff'
  },
  h1: {
    fontSize: 24,
    textAlign: 'center',
    margin: 10
  }
};

class WelcomeScreen extends Component {
  render() {
    return (
      <View style={styles.root}>
        <Text style={styles.h1}>{`React Native Navigation!`}</Text>
      </View>
    );
  }
}

export function start() {
  Navigation.onAppLaunched(() => {
    Navigation.registerContainer(`com.example.WelcomeScreen`, () => WelcomeScreen);
    Navigation.startApp({
      container: {
        name: 'com.example.WelcomeScreen'
      }
    });
  });
}

