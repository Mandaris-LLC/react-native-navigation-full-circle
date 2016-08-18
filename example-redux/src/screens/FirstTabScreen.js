import React, {Component, PropTypes} from 'react';
import {
  Text,
  View,
  ScrollView,
  TouchableOpacity,
  StyleSheet,
  Alert
} from 'react-native';
import {connect} from 'react-redux';
import * as counterActions from '../reducers/counter/actions';
import _ from 'lodash';

let topBarVisible = true;

// this is a traditional React component connected to the redux store
class FirstTabScreen extends Component {
  static navigatorStyle = {
    statusBarColor: '#303F9F',
    toolBarColor: '#3F51B5',
    navigationBarColor: '#303F9F',
    tabSelectedTextColor: '#FFA000',
    tabNormalTextColor: '#FFC107',
    tabIndicatorColor: '#FFA000',
    drawUnderTabBar: true
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
    if (event.type == 'DeepLink') {
      this.handleDeepLink(event);
    } else {
      switch (event.id) {
        case 'edit':
          Alert.alert('NavBar', 'Edit button pressed');
          break;

        case 'add':
          Alert.alert('NavBar', 'Add button pressed');
          break;

        case 'tabSelected':
          this.onTabSelected();
          break;

        default:
          console.log('Unhandled event ' + event.id);
          break;
      }
    }
  }

  handleDeepLink(event) {
    const parts = event.link.split('/');
    if (parts[0] == 'tab1' && parts[1] == 'pushScreen') {
      this.props.navigator.toggleDrawer({
        side: 'left',
        animated: true,
        to: 'closed'
      });

      this.props.navigator.push({
        title: "Pushed from SideMenu",
        screen: parts[2],
        passProps: {
          str: 'This is a prop passed in \'navigator.push()\'!',
          obj: {
            str: 'This is a prop passed in an object!',
            arr: [
              {
                str: 'This is a prop in an object in an array in an object!'
              }
            ]
          },
          num: 1234
        }
      });
    }
  }

  onTabSelected() {
    console.log('selectedTabChanged');
    this.props.navigator.setButtons({rightButtons: [
      {
        id: 'account',
        icon: require('../../img/ic_account_box_.png')
      }
    ]});
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

        <TouchableOpacity onPress={ this.onShowModalPress.bind(this) }>
          <Text style={styles.button}>Modal Screen</Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={ this.onToggleNavBarPress.bind(this) }>
          <Text style={styles.button}>Toggle NavBar</Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={ this.onSetTitlePress.bind(this) }>
          <Text style={styles.button}>Set Title</Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={ this.onSetOneButtonsPress.bind(this) }>
          <Text style={styles.button}>Set One Buttons</Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={ this.onSetTwoButtonsPress.bind(this) }>
          <Text style={styles.button}>Set Two Buttons</Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={ this.onToggleDrawerPress.bind(this) }>
          <Text style={styles.button}>Toggle Drawer</Text>
        </TouchableOpacity>

        <Text style={{fontWeight: '500'}}>String prop: {this.props.str}</Text>
        <Text style={{fontWeight: '500'}}>Function prop: {this.props.fn ? this.props.fn() : ''}</Text>
        {this.props.obj ? <Text style={{fontWeight: '500'}}>Object prop: {this.props.obj.str}</Text> : false}
        {this.props.obj && this.props.obj.arr ? <Text style={{fontWeight: '500'}}>Array prop: {this.props.obj.arr[0].str}</Text> : false}
      </View>
    );
  }

  onIncrementPress() {
    this.props.dispatch(counterActions.increment());
  }

  onPushPress() {
    this.props.navigator.push({
      title: "More",
      screen: "example.PushedScreen",
      overrideBackPress: true,
      passProps: {
        str: 'This is a prop passed in \'navigator.push()\'!',
        obj: {
          str: 'This is a prop passed in an object!',
          arr: [
            {
              str: 'This is a prop in an object in an array in an object!'
            }
          ]
        },
        num: 1234
      }
    });
  }

  onShowModalPress() {
    this.props.navigator.showModal({
      title: "Modal Screen",
      screen: "example.PushedScreen",
      passProps: {
        str: 'This is a prop passed in \'navigator.showModal()\'!',
        obj: {
          str: 'This is a prop passed in an object!',
          arr: [
            {
              str: 'This is a prop in an object in an array in an object!'
            }
          ]
        },
        num: 1234
      }
    });
  }

  onToggleNavBarPress() {
    topBarVisible = !topBarVisible;
    this.props.navigator.toggleNavBar({
      to: topBarVisible ? 'shown' : 'hidden',
      animated: true
    });
  }

  onSetTitlePress() {
    this.props.navigator.setTitle(_.random(0, 100).toString());
  }

  onSetOneButtonsPress() {
    this.props.navigator.setButtons({
      rightButtons: [
        {
          title: 'Account Box',
          icon: require('../../img/ic_account_box_.png'),
          id: 'accountBox'
        }
      ]
    });
  }

  onSetTwoButtonsPress() {
    this.props.navigator.setButtons({
      rightButtons: [
        {
          title: 'Add Alert',
          icon: require('../../img/ic_add_alert.png'),
          id: 'addAlert',
          color: '#F44336',
          enabled: false
        },
        {
          title: 'Home',
          icon: require('../../img/ic_home.png'),
          id: 'home',
          color: '#9CCC65'
        }
      ]
    })
  }

  onToggleDrawerPress() {
    this.props.navigator.toggleDrawer({
      side: 'left',
      animated: true
    })
  }
}

const styles = StyleSheet.create({
  text: {
    textAlign: 'center',
    fontSize: 18,
    marginBottom: 10,
    marginTop: 10
  },
  button: {
    textAlign: 'center',
    fontSize: 18,
    marginBottom: 10,
    marginTop: 10,
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
