import React, {
  Component,
  Text,
  View,
  ScrollView,
  TouchableOpacity,
  StyleSheet,
  Alert
} from 'react-native';
import { connect } from 'react-redux';
import * as counterActions from '../reducers/counter/actions';

// this is a traditional React component connected to the redux store
class FirstTabScreen extends Component {
  static navigatorStyle = {
    statusBarColor: '#303F9F',
    toolBarColor: '#3F51B5',
    navigationBarColor: '#303F9F',
    buttonsTint: '#FFFFFF',
    titleColor: '#FFFFFF',
    tabSelectedTextColor: '#FFA000',
    tabNormalTextColor: '#FFC107',
    tabIndicatorColor: '#FFA000'
  };

  static navigatorButtons = {
    rightButtons: [
      {
        title: 'Edit',
        id: 'edit'
      },
      {
        icon: require('../../img/navicon_add.png'),
        title: 'Add',
        id: 'add'
      }
    ]
  };
  constructor(props) {
    super(props);
    // if you want to listen on navigator events, set this up
    this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
  }
  onNavigatorEvent(event) {
    switch (event.id) {
      case 'edit':
        Alert.alert('NavBar', 'Edit button pressed');
        break;

      case 'add':
        Alert.alert('NavBar', 'Add button pressed');
        break;

      default:
        console.log('Unhandled event ' + event.id);
        break;
    }
  }
  render() {
    return (
      <View style={{flex: 1, padding: 20}}>

        <Text style={styles.text}>
          <Text style={{fontWeight: '500'}}>Same Counter: </Text> {this.props.counter.count}
        </Text>

        <TouchableOpacity onPress={ this.onIncrementPress.bind(this) }>
          <Text style={styles.button}>Increment Counter</Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={ this.onPushPress.bind(this) }>
          <Text style={styles.button}>Push Screen</Text>
        </TouchableOpacity>

      </View>
    );
  }
  onIncrementPress() {
    this.props.dispatch(counterActions.increment());
  }
  onPushPress() {
    this.props.navigator.push({
      title: "More",
      screen: "example.PushedScreen"
    });
  }
}

const styles = StyleSheet.create({
  text: {
    textAlign: 'center',
    fontSize: 18,
    marginBottom: 10,
    marginTop:10
  },
  button: {
    textAlign: 'center',
    fontSize: 18,
    marginBottom: 10,
    marginTop:10,
    color: 'blue'
  }
});

// which props do we want to inject, given the global state?
function mapStateToProps(state) {
  return {
    counter: state.counter
  };
}

export default connect(mapStateToProps)(FirstTabScreen);
