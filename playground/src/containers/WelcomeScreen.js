import React, { Component } from 'react';
import { View, Text, Button } from 'react-native';

import Navigation from 'react-native-navigation';

class WelcomeScreen extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <View style={styles.root}>
        <Text style={styles.h1}>{`React Native Navigation!`}</Text>
        <Button title="Switch to tab based app" onPress={this.onClickSwitchToTabs} />
        <Button title="Switch to tab based app with side menus" onPress={this.onClickSwitchToTabsWithSideMenus} />
      </View>
    );
  }

  onClickSwitchToTabs() {
    Navigation.setRoot({
      tabs: [
        {
          container: {
            name: 'com.example.SimpleScreen',
            passProps: {
              text: 'This is tab 1'
            }
          }
        },
        {
          container: {
            name: 'com.example.SimpleScreen',
            passProps: {
              text: 'This is tab 2'
            }
          }
        }
      ]
    });
  }

  onClickSwitchToTabsWithSideMenus() {
    Navigation.setRoot({
      tabs: [
        {
          container: {
            name: 'com.example.SimpleScreen'
          }
        },
        {
          container: {
            name: 'com.example.WelcomeScreen'
          }
        },
        {
          container: {
            name: 'com.example.SimpleScreen'
          }
        }
      ],
      sideMenu: {
        left: {
          name: 'com.example.SimpleScreen'
        },
        right: {
          name: 'com.example.SimpleScreen'
        }
      }
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
