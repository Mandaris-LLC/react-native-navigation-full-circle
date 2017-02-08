import _ from 'lodash';

export default class AppCommands {
  constructor(nativeCommandsSender, layoutTreeParser, layoutTreeCrawler) {
    this.nativeCommandsSender = nativeCommandsSender;
    this.layoutTreeParser = layoutTreeParser;
    this.layoutTreeCrawler = layoutTreeCrawler;
  }

  setRoot(simpleApi) {
    console.log('AppCommands', 'setRoot');
    const input = _.cloneDeep(simpleApi);
    const layout = this.layoutTreeParser.parseFromSimpleJSON(input);
    this.layoutTreeCrawler.crawl(layout);
    console.log('layout: ', JSON.stringify(layout));
    return this.nativeCommandsSender.setRoot(layout);
  }
}

