const React = require('react');
const { Component } = require('react');
const { View, Text, Button } = require('react-native');

const Navigation = require('react-native-navigation');

class OrientationMenuScreen extends Component {
  constructor(props) {
    super(props);
    this.onClickPushOrientationScreen = this.onClickPushOrientationScreen.bind(this);
  }

  render() {
    return (
      <View style={styles.root}>
        <Text style={styles.h1}>{`Orientation Menu`}</Text>
        <Button title="Push landscape only screen" onPress={() => this.onClickPushOrientationScreen(['landscape'])} />
        <Button title="Push portrait only screen" onPress={() => this.onClickPushOrientationScreen('portrait')} />
        <Button title="Push landscape and portrait" onPress={() => this.onClickPushOrientationScreen(['landscape', 'portrait'])} />
        <Button title="Push default" onPress={() => this.onClickPushOrientationScreen('default')} />
      </View>
    );
  }

  onClickPushOrientationScreen(orientation) {
    Navigation.push(this.props.containerId, {
      name: 'navigation.playground.OrientationScreen',
      passProps: {
        orientation
      }
    });
  }
}

module.exports = OrientationMenuScreen;

const styles = {
  root: {
    flexGrow: 1,
    justifyContent: 'center',
    alignItems: 'center'
  },
  h1: {
    fontSize: 24,
    textAlign: 'center',
    margin: 30
  },
  footer: {
    fontSize: 10,
    color: '#888',
    marginTop: 10
  }
};
