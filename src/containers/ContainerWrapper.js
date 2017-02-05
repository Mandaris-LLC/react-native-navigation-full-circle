import React, { Component } from 'react';

export default class ContainerWrapper {
  static wrap(containerName, OriginalContainer, store) {
    return class extends Component {
      constructor(props) {
        super(props);
        this._assertId(props);
        this.state = {
          id: props.id,
          allProps: { ...props, ...store.getPropsForContainerId(props.id) }
        };
      }

      _assertId(props) {
        if (!props.id) {
          throw new Error(`Container ${containerName} does not have an id!`);
        }
      }

      componentWillReceiveProps(nextProps) {
        this.setState({
          allProps: { ...nextProps, ...store.getPropsForContainerId(this.state.id) }
        });
      }

      render() {
        return (
          <OriginalContainer
            {...this.state.allProps}
            id={this.state.id}
            key={this.state.id}
          />
        );
      }
    };
  }
}
