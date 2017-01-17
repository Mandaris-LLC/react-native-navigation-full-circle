import React, {Component} from 'react';

export default class ContainerWrapper {
  static wrap(containerName, OriginalContainer, propStore) {
    return class extends Component {
      constructor(props) {
        super(props);
        if (!props.containerId) {
          throw new Error(`Container ${containerName} does not have a containerId!`);
        }
        this.state = {
          containerId: props.containerId,
          allProps: {...props, ...propStore.getPropsForContainerId(props.containerId)}
        };
      }

      componentWillReceiveProps(nextProps) {
        this.setState({
          allProps: {...nextProps, ...propStore.getPropsForContainerId(this.state.containerId)}
        });
      }

      render() {
        return (
          <OriginalContainer
            {...this.state.allProps}
            containerId={this.state.containerId}
          />
        );
      }
    };
  }
}
