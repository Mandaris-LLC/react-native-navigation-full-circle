const Container = require('./Container');
const SideMenu = require('./SideMenu');

class Root {
  /**
   * @property {Container} component
   * @property {SideMenu} [sideMenu]
   * @property {Container[]} [bottomTabs]
   */
  constructor(root) {
    this.component = root.container && new Container(root.component);
    this.sideMenu = root.sideMenu && new SideMenu(root.sideMenu);
    if (root.bottomTabs) {
      root.bottomTabs.map((t) => new Container(t.component));
      this.bottomTabs = root.bottomTabs;
    }
  }
}

module.exports = Root;
