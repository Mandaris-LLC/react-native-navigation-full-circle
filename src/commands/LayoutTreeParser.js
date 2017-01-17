import _ from 'lodash';

export default class LayoutTreeParser {
  constructor(uniqueIdProvider) {
    this.uniqueIdProvider = uniqueIdProvider;
  }

  parse(params) {
    const layout = _.cloneDeep(params);
    if (layout.container) {
      layout.container.id = this.uniqueIdProvider.generate(`container`);
    }
    if (layout.sideMenu) {
      if (layout.sideMenu.left) {
        layout.sideMenu.left.id = this.uniqueIdProvider.generate(`container`);
      }
      if (layout.sideMenu.right) {
        layout.sideMenu.right.id = this.uniqueIdProvider.generate(`container`);
      }
    }
    if (layout.tabs) {
      _.forEach(layout.tabs, (t) => t.container.id = this.uniqueIdProvider.generate(`container`));
    }
    return layout;
  }
}
