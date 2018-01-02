const LayoutTreeParser = require('./LayoutTreeParser');
const Examples = require('./Examples');
const LayoutTypes = require('./LayoutTypes');
const _ = require('lodash');

describe('LayoutTreeParser', () => {
  let uut;

  beforeEach(() => {
    uut = new LayoutTreeParser();
  });

  describe('parses into { type, data, children }', () => {
    it('unknown type', () => {
      expect(() => uut.parse({ wut: {} })).toThrow(new Error('unknown LayoutType "wut"'));
    });

    it('single component', () => {
      expect(uut.parse(Examples.singleComponent)).toEqual({
        type: LayoutTypes.Component,
        data: { name: 'MyReactComponent' },
        children: []
      });
    });

    it('pass props', () => {
      const result = uut.parse({
        component: {
          name: 'MyScreen',
          passProps: Examples.passProps
        }
      });
      expect(result).toEqual({
        type: LayoutTypes.Component,
        data: { name: 'MyScreen', passProps: Examples.passProps },
        children: []
      });
      expect(result.data.passProps).toBe(Examples.passProps);
      expect(result.data.passProps.fnProp()).toEqual('Hello from a function');
    });

    it('stack of components with top bar', () => {
      expect(uut.parse(Examples.stackOfComponentsWithTopBar)).toEqual({
        type: LayoutTypes.ComponentStack,
        data: {},
        children: [
          {
            type: LayoutTypes.Component,
            data: { name: 'MyReactComponent1' },
            children: []
          },
          {
            type: LayoutTypes.Component,
            data: { name: 'MyReactComponent2', options: Examples.topBarOptions },
            children: []
          }
        ]
      });
    });

    it('bottom tabs', () => {
      const result = uut.parse(Examples.bottomTabs);
      expect(_.keys(result)).toEqual(['type', 'data', 'children']);
      expect(result.type).toEqual(LayoutTypes.BottomTabs);
      expect(result.data).toEqual({});
      expect(result.children.length).toEqual(3);
      expect(result.children[0].type).toEqual(LayoutTypes.ComponentStack);
      expect(result.children[1].type).toEqual(LayoutTypes.ComponentStack);
      expect(result.children[2].type).toEqual(LayoutTypes.Component);
    });

    it('side menus', () => {
      const result = uut.parse(Examples.sideMenu);
      expect(_.keys(result)).toEqual(['type', 'data', 'children']);
      expect(result.type).toEqual(LayoutTypes.SideMenuRoot);
      expect(result.data).toEqual({});
      expect(result.children.length).toEqual(3);
      expect(result.children[0].type).toEqual(LayoutTypes.SideMenuLeft);
      expect(result.children[1].type).toEqual(LayoutTypes.SideMenuCenter);
      expect(result.children[2].type).toEqual(LayoutTypes.SideMenuRight);
      expect(result.children[0].children.length).toEqual(1);
      expect(result.children[0].children[0].type).toEqual(LayoutTypes.Component);
      expect(result.children[1].children.length).toEqual(1);
      expect(result.children[1].children[0].type).toEqual(LayoutTypes.ComponentStack);
      expect(result.children[2].children.length).toEqual(1);
      expect(result.children[2].children[0].type).toEqual(LayoutTypes.Component);
    });

    it('side menu center is require', () => {
      expect(() => uut.parse({ sideMenu: {} })).toThrow(new Error('sideMenu.center is required'));
    });

    it('top tabs', () => {
      const result = uut.parse(Examples.topTabs);
      expect(_.keys(result)).toEqual(['type', 'data', 'children']);
      expect(result.type).toEqual(LayoutTypes.TopTabs);
      expect(result.data).toEqual({});
      expect(result.children.length).toEqual(5);
      expect(result.children[0].type).toEqual(LayoutTypes.Component);
      expect(result.children[1].type).toEqual(LayoutTypes.Component);
      expect(result.children[2].type).toEqual(LayoutTypes.Component);
      expect(result.children[3].type).toEqual(LayoutTypes.Component);
      expect(result.children[4].type).toEqual(LayoutTypes.ComponentStack);
    });

    it('complex layout example', () => {
      const result = uut.parse(Examples.complexLayout);
      expect(result.type).toEqual('SideMenuRoot');
      expect(result.children[1].type).toEqual('SideMenuCenter');
      expect(result.children[1].children[0].type).toEqual('BottomTabs');
      expect(result.children[1].children[0].children[2].type).toEqual('ComponentStack');
      expect(result.children[1].children[0].children[2].children[0].type).toEqual('TopTabs');
      expect(result.children[1].children[0].children[2].children[0].children[2].type).toEqual('TopTabs');
      expect(result.children[1].children[0].children[2].children[0].children[2].children[4].type).toEqual('ComponentStack');
      // expect(result.children[1].children[0].children[2].children[0].children[2].data).toEqual({ options: {} });
    });
  });
});
