import React, {Component} from 'react';
import {
  StyleSheet,
  View,
  Text,
  Button
} from 'react-native';

import Navigation from 'react-native-navigation';

class ModalScreen extends Component {
  constructor(props) {
    super(props);
    this.onClickShowModal = this.onClickShowModal.bind(this);
    this.onClickDismissModal = this.onClickDismissModal.bind(this);
  }
  render() {
    return (
      <View style={styles.root}>
        <Text style={styles.h1}>Modal Screen</Text>
        <Text style={styles.footer}>ModalStackPosition: {this.props.modalPosition || 1}</Text>
        <Button title="Show Modal" onPress={this.onClickShowModal} />
        <Button title="Dismiss Modal" onPress={this.onClickDismissModal} />
        <Text style={styles.footer}>ContainerId: {this.props.id}</Text>
      </View>
    );
  }
  
  onClickShowModal() {
    Navigation.showModal({
      name: 'navigation.playground.ModalScreen',
      passProps: {
        modalPosition: (this.props.modalPosition || 1) + 1
      }
    });
  }
  
  onClickDismissModal() {
    Navigation.dismissModal(this.props.id);
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

export default ModalScreen;
