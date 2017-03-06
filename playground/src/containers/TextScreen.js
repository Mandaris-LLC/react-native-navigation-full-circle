import React, {Component} from 'react';
import {
  StyleSheet,
  View,
  Text
} from 'react-native';


class TextScreen extends Component {
  
  render() {
    return (
      <View style={styles.root}>
        <Text style={styles.h1}>{this.props.text || 'Text Screen'}</Text>
        <Text style={styles.footer}>ContainerId: {this.props.id}</Text>
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
  },
  h2: {
    fontSize: 12,
    textAlign: 'center',
    margin: 10
  },
  footer: {
    fontSize: 10,
    color: '#888',
    marginTop: 10
  }
};


export default TextScreen;
