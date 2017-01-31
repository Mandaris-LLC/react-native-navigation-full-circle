import LayoutTypes from './LayoutTypes';

describe('LayoutTreeCrawler', () => {
  let uut;
  let store;

  beforeEach(() => {
    const LayoutTreeCrawler = require('./LayoutTreeCrawler').default;
    const uniqueIdProvider = { generate: (prefix) => `${prefix}+UNIQUE_ID` };
    const Store = require('../containers/Store').default;
    store = new Store();

    uut = new LayoutTreeCrawler(uniqueIdProvider, store);
  });

  it('crawls a layout tree and adds unique id to each node', () => {
    const node = { type: LayoutTypes.Container, children: [{ type: LayoutTypes.Tabs }] };
    uut.crawl(node);
    expect(node.id).toEqual('Container+UNIQUE_ID');
    expect(node.children[0].id).toEqual('Tabs+UNIQUE_ID');
  });

  it('crawls a layout tree and ensures data exists', () => {
    const node = { type: LayoutTypes.Container, children: [{ type: LayoutTypes.Tabs }] };
    uut.crawl(node);
    expect(node.data).toEqual({});
    expect(node.children[0].data).toEqual({});
  });

  it('crawls a layout tree and ensures children exists', () => {
    const node = { type: LayoutTypes.Container, children: [{ type: LayoutTypes.Tabs }] };
    uut.crawl(node);
    expect(node.children[0].children).toEqual([]);
  });

  it('crawls a layout tree and asserts known layout type', () => {
    const node = { type: LayoutTypes.Container, children: [{ type: 'Bob' }] };
    expect(() => uut.crawl(node)).toThrow(new Error('Unknown layout type Bob'));
  });

  it('saves passProps into store for Container nodes', () => {
    const node = { type: LayoutTypes.Tabs, children: [{ type: LayoutTypes.Container, data: { passProps: { myProp: 123 } } }] };
    expect(store.getPropsForContainerId('Container+UNIQUE_ID')).toEqual({});
    uut.crawl(node);
    expect(store.getPropsForContainerId('Container+UNIQUE_ID')).toEqual({ myProp: 123 });
  });
});
