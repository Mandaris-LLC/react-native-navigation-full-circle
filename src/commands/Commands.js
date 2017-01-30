import LayoutTreeParser from './LayoutTreeParser';

export default class Commands {
  constructor(nativeCommandsSender, uniqueIdProvider) {
    this.nativeCommandsSender = nativeCommandsSender;
    this.layoutTreeParser = new LayoutTreeParser(uniqueIdProvider);
  }

  setRoot(simpleApi) {
    this.nativeCommandsSender.setRoot(this.layoutTreeParser.parseFromSimpleJSON(simpleApi));
  }
}
