import React, { Component } from 'react';
import { View, Text, Button } from 'react-native';

import Navigation from 'react-native-navigation';

class WelcomeScreen extends Component {
  render() {
    return (
      <View style={styles.root}>
        <Text style={styles.h1}>{`React Native Navigation!`}</Text>
        <Button title="Switch to tab based app" onPress={this.onClickSwitchToTabs} />
      </View>
    );
  }

  onClickSwitchToTabs() {
    Navigation.startApp({
      tabs: [
        {
          container: {
            name: 'com.example.SimpleTabScreen'
          }
        },
        {
          container: {
            name: 'com.example.WelcomeScreen'
          }
        }
      ]
    });
  }
}

export default WelcomeScreen;

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
