const Container = require('./Container');

class SideMenu {
  /**
  * @property {Container} [left]
  * @property {Container} [right]
  */
  constructor(params) {
    this.left = params.left && { component: new Container(params.left.component) };
    this.right = params.right && { component: new Container(params.right.component) };
  }
}

module.exports = SideMenu;
