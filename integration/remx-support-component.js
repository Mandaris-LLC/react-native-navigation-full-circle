import {Text} from 'react-native';
import React, {Component} from 'react';

import {connect} from 'remx/react-native';

import {selectors} from './remx-support-store';

class MyContainer extends Component {
  constructor(props) {
    super(props);
    this.renders = 0;
  }

  render() {
    this.renders++;
    if (this.props.printAge) {
      return this.renderAge();
    } else {
      return this.renderName();
    }
  }

  renderName() {
    return (
      <Text>{selectors.getName()}</Text>
    );
  }

  renderAge() {
    return (
      <Text>{selectors.getAge()}</Text>
    );
  }
}

export default connect(MyContainer);
