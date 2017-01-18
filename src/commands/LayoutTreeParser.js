import _ from 'lodash';

export default class LayoutTreeParser {
  constructor(uniqueIdProvider) {
    this.uniqueIdProvider = uniqueIdProvider;
  }

  parseSimpleApi(params) {
    const layout = this.createContainerStackWithContainer(params.container);

    //const layout = _.cloneDeep(params);
    //if (layout.container) {
    //  this.generateIdFor(layout.container);
    //}
    //if (layout.sideMenu) {
    //  if (layout.sideMenu.left) {
    //    this.generateIdFor(layout.sideMenu.left);
    //  }
    //  if (layout.sideMenu.right) {
    //    this.generateIdFor(layout.sideMenu.right);
    //  }
    //}
    //if (layout.tabs) {
    //  _.forEach(layout.tabs, this.generateIdFor);
    //}
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
