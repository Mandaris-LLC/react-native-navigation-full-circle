import _ from 'lodash';
import LayoutTreeParser from './LayoutTreeParser';
import LayoutTreeCrawler from './LayoutTreeCrawler';

export default class Commands {
  constructor(nativeCommandsSender, uniqueIdProvider, store) {
    this.nativeCommandsSender = nativeCommandsSender;
    this.uniqueIdProvider = uniqueIdProvider;
    this.layoutTreeParser = new LayoutTreeParser();
    this.layoutTreeCrawler = new LayoutTreeCrawler(uniqueIdProvider, store);
  }

  setRoot(simpleApi) {
    const input = _.cloneDeep(simpleApi);
    const layout = this.layoutTreeParser.parseFromSimpleJSON(input);
    this.layoutTreeCrawler.crawl(layout);
    return this.nativeCommandsSender.setRoot(layout);
  }
}
