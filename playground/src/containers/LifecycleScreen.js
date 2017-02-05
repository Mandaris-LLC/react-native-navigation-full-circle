import React, { Component } from 'react';
import { View, Text } from 'react-native';

class LifecycleScreen extends Component {
  constructor(props) {
    super(props);
    this.state = {
      text: 'nothing yet'
    };
  }

  onStart() {
    this.setState({ text: 'onStart!' });
  }

  onStop() {
    this.setState({ text: 'onStop!' });
  }

  render() {
    return (
      <View style={styles.root}>
        <Text style={styles.h1}>{this.state.text}</Text>
      </View>
    );
  }
}
export default LifecycleScreen;

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
