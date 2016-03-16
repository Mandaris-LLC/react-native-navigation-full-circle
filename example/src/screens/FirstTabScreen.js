import React, {
  Text,
  View,
  ScrollView,
  TouchableOpacity,
  StyleSheet,
  AlertIOS
} from 'react-native';

// important imports, the magic is here
import { Navigation, Screen } from 'react-native-navigation';

// need to import every screen we push
import './PushedScreen';
import './StyledScreen';
import './ModalScreen';

// instead of React.Component, we extend Screen (imported above)
class FirstTabScreen extends Screen {
  static navigatorButtons = {
    leftButtons: [{
      icon: require('../../img/navicon_menu.png'),
      id: 'menu'
    }],
    rightButtons: [
      {
        title: 'Edit',
        id: 'edit'
      },
      {
        icon: require('../../img/navicon_add.png'),
        id: 'add'
      }
    ]
  };
  constructor(props) {
    super(props);
  }
  onNavigatorEvent(event) {
    if (event.id == 'menu') {
      this.navigator.toggleDrawer({
        side: 'left',
        animated: true
      });
    }
    if (event.id == 'edit') {
      AlertIOS.alert('NavBar', 'Edit button pressed');
    }
    if (event.id == 'add') {
      AlertIOS.alert('NavBar', 'Add button pressed');
    }
  }
  render() {
    return (
      <View style={{flex: 1, padding: 20}}>

        <TouchableOpacity onPress={ this.onPushPress.bind(this) }>
          <Text style={styles.button}>Push Plain Screen</Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={ this.onPushStyledPress.bind(this) }>
          <Text style={styles.button}>Push Styled Screen</Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={ this.onModalPress.bind(this) }>
          <Text style={styles.button}>Show Modal Screen</Text>
        </TouchableOpacity>

      </View>
    );
  }
  onPushPress() {
    this.navigator.push({
      title: "More",
      screen: "example.PushedScreen"
    });
  }
  onPushStyledPress() {
    this.navigator.push({
      title: "Styled",
      screen: "example.StyledScreen"
    });
  }
  onModalPress() {
    this.navigator.showModal({
      title: "Modal",
      screen: "example.ModalScreen"
    });
  }
}

const styles = StyleSheet.create({
  button: {
    textAlign: 'center',
    fontSize: 18,
    marginBottom: 10,
    marginTop:10,
    color: 'blue'
  }
});

// every screen must be registered with a unique name
Navigation.registerScreen('example.FirstTabScreen', () => FirstTabScreen);
