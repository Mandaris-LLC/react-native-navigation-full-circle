import React, { Component } from 'react';
import { View, Text, Button } from 'react-native';

import Navigation from 'react-native-navigation';

class SimpleScreen extends Component {
  constructor(props) {
    super(props);
    this.onClickPop = this.onClickPop.bind(this);
    this.onClickPush = this.onClickPush.bind(this);
    this.onClickShowModal = this.onClickShowModal.bind(this);
    this.onClickDismissModal = this.onClickDismissModal.bind(this);
  }

  render() {
    return (
      <View style={styles.root}>
        <Text style={styles.h1}>{this.props.text || 'Simple Screen'}</Text>
        <Text style={styles.h2}>{this.props.stackPosition}</Text>
        {this.renderTextFromFunctionInProps()}
        <Button title="Push" onPress={this.onClickPush} />
        <Button title="Pop" onPress={this.onClickPop} />
        <Button title="Show Mddal" onPress={this.onClickPop} />
        <Button title="Dismiss Modal" onPress={this.onClickDismissModal} />
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

  onClickPush() {
    Navigation.on(this.props.id).push({
      name: 'navigation.playground.SimpleScreen',
      passProps: {
        stackPosition: this.props.stackPosition + 1
      }
    });
  }
  
  onClickShowModal() {
    Navigation.showModal({
      name: 'navigation.playground.SimpleScreen',
      passProps: {
        text: 'Modal screen'
      }
    });
  }

  onClickDismissModal() {
    Navigation.dismissModal(this.props.id);
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
  },
  h2: {
    fontSize: 12,
    textAlign: 'center',
    margin: 10
  }
};
