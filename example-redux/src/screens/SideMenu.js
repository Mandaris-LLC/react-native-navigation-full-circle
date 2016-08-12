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

class SideMenu extends Component {

  constructor(props) {
    super(props);
    // if you want to listen on navigator events, set this up
    this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
  }

  onNavigatorEvent(event) {
      console.log('Unhandled event ' + event.id);
  }

  render() {
    return (
      <View style={{flex: 1, padding: 20, backgroundColor: 'green'}}>
        <Text>Hello from SideMenu</Text>
      </View>
    );
  }
}

export default connect()(SideMenu);