import React, {
  Component,
  Text,
  View,
  ScrollView,
  TouchableOpacity,
  StyleSheet,
  AlertIOS
} from 'react-native';

export default class SecondTabScreen extends Component {
  constructor(props) {
    super(props);
    this.buttonsCounter = 0;
    // if you want to listen on navigator events, set this up
    this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
  }
  render() {
    return (
      <View style={{flex: 1, padding: 20}}>

        <TouchableOpacity onPress={ this.onChangeButtonsPress.bind(this) }>
          <Text style={styles.button}>Change Buttons</Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={ this.onChangeTitlePress.bind(this) }>
          <Text style={styles.button}>Change Title</Text>
        </TouchableOpacity>

      </View>
    );
  }
  onChangeTitlePress() {
    this.props.navigator.setTitle({
      title: Math.round(Math.random() * 100000).toString()
    });
  }
  onChangeButtonsPress() {
    let buttons;
    if (this.buttonsCounter % 3 == 0) {
      buttons = [
        {
          title: 'Edit',
          id: 'edit'
        },
        {
          icon: require('../../img/navicon_add.png'),
          id: 'add'
        }
      ];
    } else if (this.buttonsCounter % 3 == 1) {
      buttons = [{
        title: 'Save',
        id: 'save'
      }];
    } else {
      buttons = [];
    }
    this.buttonsCounter++;

    this.props.navigator.setButtons({
      rightButtons: buttons,
      animated: true
    });
  }
  onNavigatorEvent(event) {
    // handle a deep link
    if (event.type == 'DeepLink') {
      const parts = event.link.split('/');
      if (parts[0] == 'tab2') {
        this.props.navigator.resetTo({
          title: "Replaced Root",
          screen: parts[1],
          animated: true
        });
      }
    }
    // handle a button press
    if (event.type == 'NavBarButtonPress') {
      if (event.id == 'edit') {
        AlertIOS.alert('NavBar', 'Dynamic Edit button pressed');
      }
      if (event.id == 'add') {
        AlertIOS.alert('NavBar', 'Dynamic Add button pressed');
      }
      if (event.id == 'save') {
        AlertIOS.alert('NavBar', 'Dynamic Save button pressed');
      }
    }
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
