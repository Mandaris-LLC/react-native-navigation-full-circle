import React, { Component } from 'react';

export default class ContainerWrapper {
  static wrap(containerName, OriginalContainer, propStore) {
    return class extends Component {
      constructor(props) {
        super(props);
        if (!props.id) {
          throw new Error(`Container ${containerName} does not have an id!`);
        }
        this.state = {
          id: props.id,
          allProps: { ...props, ...propStore.getPropsForContainerId(props.id) }
        };
      }

      componentWillReceiveProps(nextProps) {
        this.setState({
          allProps: { ...nextProps, ...propStore.getPropsForContainerId(this.state.id) }
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
