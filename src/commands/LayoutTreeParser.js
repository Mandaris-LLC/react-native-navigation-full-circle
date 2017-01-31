import * as _ from 'lodash';
import LayoutTypes from './LayoutTypes';

export default class LayoutTreeParser {
  /**
   * returns correct layout tree of {type, children, data?}
   */
  parseFromSimpleJSON(simpleJsonApi) {
    if (simpleJsonApi.sideMenu) {
      return this._createSideMenu(simpleJsonApi);
    }
    if (simpleJsonApi.tabs) {
      return this._createTabs(simpleJsonApi.tabs);
    }
    return this._createContainerStackWithContainer(simpleJsonApi.container);
  }

  _createTabs(tabs) {
    return {
      type: LayoutTypes.Tabs,
      children: _.map(tabs, (t) => this._createContainerStackWithContainer(t.container))
    };
  }

  _createContainerStackWithContainer(container) {
    return {
      type: LayoutTypes.ContainerStack,
      children: [this._createContainer(container)]
    };
  }

  _createContainer(container) {
    return {
      type: LayoutTypes.Container,
      data: container,
      children: []
    };
  }

  _createSideMenu(layout) {
    return {
      type: LayoutTypes.SideMenuRoot,
      children: this._createSideMenuChildren(layout)
    };
  }

  _createSideMenuChildren(layout) {
    const children = [];
    if (layout.sideMenu.left) {
      children.push({
        type: LayoutTypes.SideMenuLeft,
        children: [this._createContainer(layout.sideMenu.left.container)]
      });
    }
    children.push({
      type: LayoutTypes.SideMenuCenter,
      children: [
        layout.tabs ? this._createTabs(layout.tabs) : this._createContainerStackWithContainer(layout.container)
      ]
    });
    if (layout.sideMenu.right) {
      children.push({
        type: LayoutTypes.SideMenuRight,
        children: [this._createContainer(layout.sideMenu.right.container)]
      });
    }
    return children;
  }
}
