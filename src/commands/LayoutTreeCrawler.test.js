import LayoutTypes from './LayoutTypes';
import LayoutTreeCrawler from './LayoutTreeCrawler';
import Store from '../containers/Store';
import UniqueIdProvider from '../adapters/UniqueIdProvider.mock';

describe('LayoutTreeCrawler', () => {
  let uut;
  let store;

  beforeEach(() => {
    store = new Store();
    uut = new LayoutTreeCrawler(new UniqueIdProvider(), store);
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
