import React, { Component } from 'react';
import { Text } from 'react-native';

import { connect } from 'remx/react-native';

import * as store from './store';

class MyContainer extends Component {
  render() {
    if (this.props.renderCountIncrement) {
      this.props.renderCountIncrement();
    }

    return this.renderText(this.props.printAge ? this.props.age : this.props.name);
  }

  renderText(txt) {
    return (
      <Text>{txt}</Text>
    );
  }
}

function mapStateToProps() {
  return {
    name: store.getters.getName(),
    age: store.getters.getAge()
  };
}

export default connect(mapStateToProps)(MyContainer);
