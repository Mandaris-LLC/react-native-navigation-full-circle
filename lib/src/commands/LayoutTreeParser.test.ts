import * as  _ from 'lodash';
import { LayoutTreeParser } from './LayoutTreeParser';
import { LayoutType } from './values/LayoutType';

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
      expect(uut.parse(LayoutExamples.singleComponent)).toEqual({
        type: LayoutType.Component,
        data: { name: 'MyReactComponent', options: LayoutExamples.options, passProps: LayoutExamples.passProps },
        children: []
      });
    });

    it('pass props', () => {
      const result = uut.parse({
        component: {
          name: 'MyScreen',
          passProps: LayoutExamples.passProps
        }
      });
      expect(result).toEqual({
        type: LayoutType.Component,
        data: { name: 'MyScreen', passProps: LayoutExamples.passProps },
        children: []
      });
      expect(result.data.passProps).toBe(LayoutExamples.passProps);
      expect(result.data.passProps.fnProp()).toEqual('Hello from a function');
    });

    it('stack of components with top bar', () => {
      expect(uut.parse(LayoutExamples.stackWithTopBar)).toEqual({
        type: LayoutType.Stack,
        data: {
          options: LayoutExamples.options
        },
        children: [
          {
            type: LayoutType.Component,
            data: { name: 'MyReactComponent1' },
            children: []
          },
          {
            type: LayoutType.Component,
            data: { name: 'MyReactComponent2', options: LayoutExamples.options },
            children: []
          }
        ]
      });
    });

    it('bottom tabs', () => {
      const result = uut.parse(LayoutExamples.bottomTabs);
      expect(_.keys(result)).toEqual(['type', 'data', 'children']);
      expect(result.type).toEqual(LayoutType.BottomTabs);
      expect(result.data).toEqual({});
      expect(result.children.length).toEqual(3);
      expect(result.children[0].type).toEqual(LayoutType.Stack);
      expect(result.children[1].type).toEqual(LayoutType.Stack);
      expect(result.children[2].type).toEqual(LayoutType.Component);
    });

    it('side menus', () => {
      const result = uut.parse(LayoutExamples.sideMenu);
      expect(_.keys(result)).toEqual(['type', 'data', 'children']);
      expect(result.type).toEqual(LayoutType.SideMenuRoot);
      expect(result.data).toEqual({});
      expect(result.children.length).toEqual(3);
      expect(result.children[0].type).toEqual(LayoutType.SideMenuLeft);
      expect(result.children[1].type).toEqual(LayoutType.SideMenuCenter);
      expect(result.children[2].type).toEqual(LayoutType.SideMenuRight);
      expect(result.children[0].children.length).toEqual(1);
      expect(result.children[0].children[0].type).toEqual(LayoutType.Component);
      expect(result.children[1].children.length).toEqual(1);
      expect(result.children[1].children[0].type).toEqual(LayoutType.Stack);
      expect(result.children[2].children.length).toEqual(1);
      expect(result.children[2].children[0].type).toEqual(LayoutType.Component);
    });

    it('side menu center is require', () => {
      expect(() => uut.parse({ sideMenu: {} })).toThrow(new Error('sideMenu.center is required'));
    });

    it('top tabs', () => {
      const result = uut.parse(LayoutExamples.topTabs);
      expect(_.keys(result)).toEqual(['type', 'data', 'children']);
      expect(result.type).toEqual(LayoutType.TopTabs);
      expect(result.data).toEqual({ options: LayoutExamples.options });
      expect(result.children.length).toEqual(5);
      expect(result.children[0].type).toEqual(LayoutType.Component);
      expect(result.children[1].type).toEqual(LayoutType.Component);
      expect(result.children[2].type).toEqual(LayoutType.Component);
      expect(result.children[3].type).toEqual(LayoutType.Component);
      expect(result.children[4].type).toEqual(LayoutType.Stack);
    });

    it('complex layout example', () => {
      const result = uut.parse(LayoutExamples.complexLayout);
      expect(result.type).toEqual('SideMenuRoot');
      expect(result.children[1].type).toEqual('SideMenuCenter');
      expect(result.children[1].children[0].type).toEqual('BottomTabs');
      expect(result.children[1].children[0].children[2].type).toEqual('Stack');
      expect(result.children[1].children[0].children[2].children[0].type).toEqual('TopTabs');
      expect(result.children[1].children[0].children[2].children[0].children[2].type).toEqual('TopTabs');
      expect(result.children[1].children[0].children[2].children[0].children[2].children[4].type).toEqual('Stack');
      expect(result.children[1].children[0].children[2].children[0].children[2].data).toEqual({ options: { topBar: { title: 'Hello1' } } });
    });
  });

  it('options for all containing types', () => {
    const options = {};
    expect(uut.parse({ component: { options } }).data.options).toBe(options);
    expect(uut.parse({ stack: { options } }).data.options).toBe(options);
    expect(uut.parse({ bottomTabs: { options } }).data.options).toBe(options);
    expect(uut.parse({ topTabs: { options } }).data.options).toBe(options);
    expect(uut.parse({ sideMenu: { options, center: { component: {} } } }).data.options).toBe(options);
  });
});

const LayoutExamples = {
  passProps: {
    strProp: 'string prop',
    numProp: 12345,
    objProp: { inner: { foo: 'bar' } },
    fnProp: () => 'Hello from a function'
  },

  options: {
    topBar: {
      title: 'Hello1'
    }
  },

  singleComponent: {
    component: {
      name: 'MyReactComponent',
      options: this.options,
      passProps: this.passProps
    }
  },

  stackWithTopBar: {
    stack: {
      children: [
        {
          component: {
            name: 'MyReactComponent1'
          }
        },
        {
          component: {
            name: 'MyReactComponent2',
            options: this.options
          }
        }
      ],
      options: this.options
    }
  },

  bottomTabs: {
    bottomTabs: {
      children: [
        { ...this.stackWithTopBar },
        { ...this.stackWithTopBar },
        {
          component: {
            name: 'MyReactComponent1'
          }
        }
      ]
    }
  },

  sideMenu: {
    sideMenu: {
      left: { ...this.singleComponent },
      center: { ...this.stackWithTopBar },
      right: { ...this.singleComponent }
    }
  },

  topTabs: {
    topTabs: {
      children: [
        { ...this.singleComponent },
        { ...this.singleComponent },
        { ...this.singleComponent },
        { ...this.singleComponent },
        { ...this.stackWithTopBar }
      ],
      options: this.options
    }
  },

  complexLayout: {
    sideMenu: {
      left: { ...this.singleComponent },
      center: {
        bottomTabs: {
          children: [
            { ...this.stackWithTopBar },
            { ...this.stackWithTopBar },
            {
              stack: {
                children: [
                  {
                    topTabs: {
                      children: [
                        { ...this.stackWithTopBar },
                        { ...this.stackWithTopBar },
                        {
                          topTabs: {
                            options: this.options,
                            children: [
                              { ...this.singleComponent },
                              { ...this.singleComponent },
                              { ...this.singleComponent },
                              { ...this.singleComponent },
                              { ...this.stackWithTopBar }
                            ]
                          }
                        }
                      ]
                    }
                  }
                ]
              }
            }
          ]
        }
      }
    }
  }
}

