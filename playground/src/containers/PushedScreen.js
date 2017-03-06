import React, {Component} from 'react';
import {
  StyleSheet,
  View,
  Text,
  Button
} from 'react-native';

import Navigation from 'react-native-navigation';

class PushedScreen extends Component {
  constructor(props) {
    super(props);
    this.onClickPush = this.onClickPush.bind(this);
    this.onClickPop = this.onClickPop.bind(this);
  }
  render() {
    return (
      <View style={styles.root}>
        <Text style={styles.h1}>Pushed Screen</Text>
        <Text style={styles.h2}>Stack Position: {this.props.stackPosition || 1}</Text>
        <Button title="Push" onPress={this.onClickPush} />
        <Button title="Pop" onPress={this.onClickPop} />
        <Text style={styles.footer}>ContainerId: {this.props.id}</Text>
      </View>
    );
  }
  
  onClickPush() {
    Navigation.on(this.props.id).push({
      name: 'navigation.playground.PushedScreen',
      passProps: {
        stackPosition: (this.props.stackPosition || 1) + 1
      }
    });
  }
  
  onClickPop() {
    Navigation.on(this.props.id).pop();
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

export default PushedScreen;
