const Options = require('./../options/Options');

class Container {
  /**
   * @property {string} name The container's registered name
   * @property {Container[]} [topTabs]
   * @property {object} [passProps] props
   * @property {object} [customTransition]
   * @property {Options} options
   */
  constructor(params) {
    this.name = params.name;
    if (params.topTabs) {
      params.topTabs.map((t) => new Container(t));
      this.topTabs = params.topTabs;
    }
    this.passProps = params.passProps;
    this.customTransition = params.customTransition;
    this.options = params.options && new Options(params.options);
  }
}

module.exports = Container;
