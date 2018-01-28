import * as React from 'react';
import * as PropTypes from 'prop-types';
import { requireNativeComponent } from 'react-native';

interface ElementProps {
  elementId: PropTypes.Requireable<string>;
  resizeMode: any;
}

let RNNElement: React.ComponentType<any>;

export class Element extends React.Component<ElementProps, any> {
  static propTypes = {
    elementId: PropTypes.string.isRequired,
    resizeMode: PropTypes.string
  }
  static defaultProps = {
    resizeMode: ''
  }

  render() {
    return (
      <RNNElement {...this.props} />
    );
  }
}

RNNElement = requireNativeComponent('RNNElement', Element);

