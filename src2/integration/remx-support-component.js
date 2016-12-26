import {Text} from 'react-native';
import React, {Component} from 'react';

import {connect} from 'remx/react-native';

import * as store from './remx-support-store';

class MyScreen extends Component {
  constructor(props) {
    super(props);
    this.renders = 0;
  }
  render() {
    this.renders++;
    const txt = store.selectors.getName();
    return (
      <Text>{txt}</Text>
    );
  }
}

export default MyScreen;
