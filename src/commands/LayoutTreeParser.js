import * as _ from 'lodash';

const Types = {
  Container: 'Container',
  ContainerStack: 'ContainerStack',
  Tabs: 'Tabs',
  SideMenuRoot: 'SideMenuRoot',
  SideMenuCenter: 'SideMenuCenter',
  SideMenuLeft: 'SideMenuLeft',
  SideMenuRight: 'SideMenuRight'
};

export default class LayoutTreeParser {
  constructor(uniqueIdProvider) {
    this.uniqueIdProvider = uniqueIdProvider;
  }

  parseFromSimpleJSON(simpleJsonApi) {
    const input = _.cloneDeep(simpleJsonApi);
    if (input.sideMenu) {
      return this._createSideMenu(input);
    }
    if (input.tabs) {
      return this._createTabs(input.tabs);
    }
    return this._createContainerStackWithContainer(input.container);
  }

  _node(node) {
    node.id = this.uniqueIdProvider.generate(node.type);
    node.children = node.children || [];
    return node;
  }

  _createTabs(tabs) {
    return this._node({
      type: Types.Tabs,
      children: _.map(tabs, (t) => this._createContainerStackWithContainer(t.container))
    });
  }

  _createContainerStackWithContainer(container) {
    return this._node({
      type: Types.ContainerStack,
      children: [this._createContainer(container)]
    });
  }

  _createContainer(container) {
    return this._node({
      type: Types.Container,
      data: container
    });
  }

  _createSideMenu(layout) {
    return this._node({
      type: Types.SideMenuRoot,
      children: this._createSideMenuChildren(layout)
    });
  }

  _createSideMenuChildren(layout) {
    const children = [];
    if (layout.sideMenu.left) {
      children.push(this._node({
        type: Types.SideMenuLeft,
        children: [this._createContainer(layout.sideMenu.left.container)]
      }));
    }
    children.push(this._node({
      type: Types.SideMenuCenter,
      children: [
        layout.tabs ? this._createTabs(layout.tabs) : this._createContainerStackWithContainer(layout.container)
      ]
    }));
    if (layout.sideMenu.right) {
      children.push(this._node({
        type: Types.SideMenuRight,
        children: [this._createContainer(layout.sideMenu.right.container)]
      }));
    }
    return children;
  }
}
