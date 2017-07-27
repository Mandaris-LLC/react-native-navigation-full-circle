import React from 'react';
import {StyleSheet, View, Button} from 'react-native';

class MyClass extends React.Component {

  onShowModal = () => {
    this.props.navigator.toggleDrawer({
      side: 'left'
    });
    this.props.navigator.showModal({
      screen: 'example.Types.Modal',
      title: `Modal`
    });
  };

  render() {
    return (
      <View style={styles.container}>
        <View style={styles.button}>
          <Button
            onPress={this.onShowModal}
            title="Show Modal"/>
        </View>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: 300,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#ffffff',
  },
  button: {
    marginTop: 16
  }
});

export default MyClass;
