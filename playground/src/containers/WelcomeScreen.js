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
        <Button
          testId="btnSwitchToTabs"
          title="Switch to tab based app"
          onPress={this.onClickSwitchToTabs}
        />
        <Button
          testId="btnSwitchToTabsWithMenus"
          title="Switch to tab based app with side menus"
          onPress={this.onClickSwitchToTabsWithSideMenus}
        />
      </View>
    );
  }

  onClickSwitchToTabs() {
    Navigation.setRoot({
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

  onClickSwitchToTabsWithSideMenus() {
    Navigation.setRoot({
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
        },
        {
          container: {
            name: 'com.example.SimpleTabScreen'
          }
        }
      ],
      sideMenu: {
        left: {
          name: 'com.example.SimpleTabScreen'
        },
        right: {
          name: 'com.example.SimpleTabScreen'
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
