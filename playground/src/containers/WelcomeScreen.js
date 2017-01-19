import React, { Component } from 'react';
import { View, Text, Button } from 'react-native';

export default class WelcomeScreen extends Component {
  render() {
    return (
      <View style={styles.root}>
        <Text style={styles.h1}>{`React Native Navigation!`}</Text>
        <Button>
          <Text style={styles.h1}>{`Switch to tab based app`}</Text>
        </Button>
      </View>
    );
  }
}

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
