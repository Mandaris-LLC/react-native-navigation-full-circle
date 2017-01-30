import * as _ from 'lodash';

export default class LayoutTreeParser {
  constructor(uniqueIdProvider) {
    this.uniqueIdProvider = uniqueIdProvider;
  }

  parseFromSimpleJSON(simpleJsonApi) {
    // TOOD deepclone
    if (simpleJsonApi.sideMenu) {
      return {
        type: 'SideMenuRoot',
        id: this.uniqueIdProvider.generate('SideMenuRoot'),
        children: this.createSideMenuChildren(simpleJsonApi)
      };
    }
    if (simpleJsonApi.tabs) {
      return this.createTabs(simpleJsonApi.tabs);
    }

    return this.createContainerStackWithContainer(simpleJsonApi.container);
  }

  createSideMenuChildren(layout) {
    const children = [];
    if (layout.sideMenu.left) {
      children.push({
        type: 'SideMenuLeft',
        id: this.uniqueIdProvider.generate('SideMenuLeft'),
        children: [
          this.createContainer(layout.sideMenu.left.container)
        ]
      });
    }
    children.push({
      type: 'SideMenuCenter',
      id: this.uniqueIdProvider.generate('SideMenuCenter'),
      children: [
        this.createContainerStackWithContainer(layout.container)
      ]
    });
    return children;
  }

  createTabs(tabs) {
    return {
      type: 'Tabs',
      id: this.uniqueIdProvider.generate(`Tabs`),
      children: _.map(tabs, (t) => this.createContainerStackWithContainer(t.container))
    };
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
      data: container,
      type: 'Container',
      id: this.uniqueIdProvider.generate(`Container`),
      children: []
    };
  }
}
