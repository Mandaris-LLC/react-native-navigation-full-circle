import _ from 'lodash';

export default class LayoutTreeParser {
  constructor(uniqueIdProvider) {
    this.uniqueIdProvider = uniqueIdProvider;
  }

  parseFromSimpleJSON(simpleJsonApi) {
    if (simpleJsonApi.tabs) {
      return {
        type: 'Tabs',
        id: this.uniqueIdProvider.generate(`Tabs`),
        children: _.map(simpleJsonApi.tabs, (t) => this.createContainerStackWithContainer(t.container))
      };
    }
    return this.createContainerStackWithContainer(simpleJsonApi.container);
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
      data: _.merge({}, container),
      type: 'Container',
      id: this.uniqueIdProvider.generate(`Container`),
      children: []
    };
  }
}
