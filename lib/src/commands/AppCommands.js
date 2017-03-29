import _ from 'lodash';

export default class AppCommands {
  constructor(nativeCommandsSender, layoutTreeParser, layoutTreeCrawler) {
    this.nativeCommandsSender = nativeCommandsSender;
    this.layoutTreeParser = layoutTreeParser;
    this.layoutTreeCrawler = layoutTreeCrawler;
  }

  setRoot(simpleApi) {
    const input = _.cloneDeep(simpleApi);
    const layout = this.layoutTreeParser.parseFromSimpleJSON(input);
    this.layoutTreeCrawler.crawl(layout);
    return this.nativeCommandsSender.setRoot(layout);
  }

  showModal(simpleApi) {
    const input = _.cloneDeep(simpleApi);
    const layout = this.layoutTreeParser.parseFromSimpleJSON(input);
    this.layoutTreeCrawler.crawl(layout);
    return this.nativeCommandsSender.showModal(layout);
  }

  dismissModal(id) {
    return this.nativeCommandsSender.dismissModal(id);
  }

  dismissAllModals() {
    return this.nativeCommandsSender.dismissAllModals();
  }
}

