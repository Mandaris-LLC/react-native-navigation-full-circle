import * as _ from 'lodash';
import LayoutTreeParser from './LayoutTreeParser';

export default class Commands {
  constructor(nativeCommandsSender, uniqueIdProvider, store) {
    this.nativeCommandsSender = nativeCommandsSender;
    this.layoutTreeParser = new LayoutTreeParser(uniqueIdProvider);
    this.store = store;
  }

  setRoot(simpleApi) {
    const layout = this.layoutTreeParser.parseFromSimpleJSON(simpleApi);
    this._savePassProps(layout);
    this.nativeCommandsSender.setRoot(layout);
  }

  _savePassProps(node) {
    if (node.type === 'Container') {
      this.store.setPropsForContainerId(node.id, node.data.passProps);
    }
    _.forEach(node.children, (child) => this._savePassProps(child));
  }
}
