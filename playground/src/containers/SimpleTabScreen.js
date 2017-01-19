import React, {Component} from 'react';
import {View, Text} from 'react-native';

export default class SimpleTabScreen extends Component {
  render() {
    return (
      <View style={styles.root}>
        <Text style={styles.h1}>{`This is a tab screen`}</Text>
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
