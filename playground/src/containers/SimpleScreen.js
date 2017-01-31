import React, { Component } from 'react';
import { View, Text } from 'react-native';

class SimpleScreen extends Component {
  render() {
    const text = this.props.text || 'Simple Screen';
    return (
      <View style={styles.root}>
        <Text style={styles.h1}>{text}</Text>
      </View>
    );
  }
}
export default SimpleScreen;

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
