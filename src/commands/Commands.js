import LayoutTreeParser from './LayoutTreeParser';

export default class Commands {
  constructor(nativeCommandsSender, uniqueIdProvider) {
    this.nativeCommandsSender = nativeCommandsSender;
    this.layoutTreeParser = new LayoutTreeParser(uniqueIdProvider);
  }

  startApp(simpleApi) {
    this.nativeCommandsSender.startApp(this.layoutTreeParser.parseFromSimpleJSON(simpleApi));
  }
}
