import React, { Component } from 'react';
import { View, Text, Button } from 'react-native';

import Navigation from 'react-native-navigation';

class WelcomeScreen extends Component {
  constructor(props) {
    super(props);
    this.onClickPush = this.onClickPush.bind(this);
  }

  render() {
    return (
      <View style={styles.root}>
        <Text style={styles.h1}>{`React Native Navigation!`}</Text>
        <Button title="Switch to tab based app" onPress={this.onClickSwitchToTabs} />
        <Button title="Switch to app with side menus" onPress={this.onClickSwitchToSideMenus} />
        <Button title="Push" onPress={this.onClickPush} />
        <Text style={styles.footer}>{`this.props.id = ${this.props.id}`}</Text>
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
              text: 'This is tab 1',
              myFunction: () => 'Hello from a function!'
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

  onClickSwitchToSideMenus() {
    Navigation.setRoot({
      tabs: [
        {
          container: {
            name: 'com.example.SimpleScreen'
          }
        },
        {
          container: {
            name: 'com.example.SimpleScreen'
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
          container: {
            name: 'com.example.SimpleScreen'
          }
        },
        right: {
          container: {
            name: 'com.example.SimpleScreen'
          }
        }
      }
    });
  }

  onClickPush() {
    Navigation.on(this.props.id).push({
      name: 'com.example.SimpleScreen',
      passProps: {
        text: 'Pushed screen'
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
    margin: 30
  },
  footer: {
    fontSize: 10,
    color: '#888',
    marginTop: 80
  }
};
