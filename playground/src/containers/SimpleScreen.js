import React, { Component } from 'react';
import { View, Text, Button } from 'react-native';

import Navigation from 'react-native-navigation';

class SimpleScreen extends Component {
  constructor(props) {
    super(props);
    this.onClickPop = this.onClickPop.bind(this);
  }

  render() {
    return (
      <View style={styles.root}>
        <Text style={styles.h1}>{this.props.text || 'Simple Screen'}</Text>
        {this.renderTextFromFunctionInProps()}
        <Button title="Pop" onPress={this.onClickPop} />
      </View>
    );
  }

  renderTextFromFunctionInProps() {
    if (!this.props.myFunction) {
      return undefined;
    }
    return (
      <Text style={styles.h1}>{this.props.myFunction()}</Text>
    );
  }

  onClickPop() {
    Navigation.on(this.props.id).pop();
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
