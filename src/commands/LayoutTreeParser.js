import _ from 'lodash';

export default class LayoutTreeParser {
  constructor(uniqueIdProvider) {
    this.uniqueIdProvider = uniqueIdProvider;
  }

  parseSimpleJSON(params) {
    const layout = this.createContainerStackWithContainer(params.container);
    return layout;
  }

  createContainerStackWithContainer(container) {
    return {
      type: 'ContainerStack',
      id: this.uniqueIdProvider.generate(`ContainerStack`),
      children: [
        this.createContainer(container)
      ]
    };
  }

  createContainer(container) {
    return {
      data: {
        ...container
      },
      type: 'Container',
      id: this.uniqueIdProvider.generate(`Container`),
      children: []
    };
  }
}
