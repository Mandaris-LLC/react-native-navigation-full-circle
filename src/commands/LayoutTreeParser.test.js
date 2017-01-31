import * as SimpleLayouts from './SimpleLayouts';

describe('LayoutTreeParser', () => {
  let uut;

  beforeEach(() => {
    const LayoutTreeParser = require('./LayoutTreeParser').default;
    uut = new LayoutTreeParser();
  });

  it('parses single screen', () => {
    expect(uut.parseFromSimpleJSON(SimpleLayouts.singleScreenApp))
      .toEqual({
        type: 'ContainerStack',
        children: [
          {
            type: 'Container',
            data: {
              name: 'com.example.MyScreen'
            },
            children: []
          }
        ]
      });
  });

  it('parses single screen with props', () => {
    expect(uut.parseFromSimpleJSON(SimpleLayouts.singleScreenWithAditionalParams))
      .toEqual({
        type: 'ContainerStack',
        children: [
          {
            type: 'Container',
            children: [],
            data: {
              name: 'com.example.MyScreen',
              passProps: SimpleLayouts.passProps,
              style: {},
              buttons: {}
            }
          }
        ]
      });
    const parsedPropsFn = uut.parseFromSimpleJSON(SimpleLayouts.singleScreenWithAditionalParams)
      .children[0].data.passProps.fnProp;
    expect(parsedPropsFn).toBe(SimpleLayouts.passProps.fnProp);
    expect(parsedPropsFn()).toEqual('Hello from a function');
  });

  it('parses tab based', () => {
    expect(uut.parseFromSimpleJSON(SimpleLayouts.tabBasedApp))
      .toEqual({
        type: 'Tabs',
        children: [
          {
            type: 'ContainerStack',
            children: [
              {
                type: 'Container',
                children: [],
                data: {
                  name: 'com.example.ATab'
                }
              }
            ]
          },
          {
            type: 'ContainerStack',
            children: [
              {
                type: 'Container',
                children: [],
                data: {
                  name: 'com.example.SecondTab'
                }
              }
            ]
          },
          {
            type: 'ContainerStack',
            children: [
              {
                type: 'Container',
                children: [],
                data: {
                  name: 'com.example.ATab'
                }
              }
            ]
          }
        ]
      });
  });

  it('parses side menus', () => {
    expect(uut.parseFromSimpleJSON(SimpleLayouts.singleWithSideMenu))
      .toEqual({
        type: 'SideMenuRoot',
        children: [
          {
            type: 'SideMenuLeft',
            children: [
              {
                type: 'Container',
                data: {
                  name: 'com.example.SideMenu'
                },
                children: []
              }
            ]
          },
          {
            type: 'SideMenuCenter',
            children: [
              {
                type: 'ContainerStack',
                children: [
                  {
                    type: 'Container',
                    data: {
                      name: 'com.example.MyScreen'
                    },
                    children: []
                  }
                ]
              }
            ]
          }
        ]
      });
  });

  it('parses side menu right', () => {
    expect(uut.parseFromSimpleJSON(SimpleLayouts.singleWithRightSideMenu))
      .toEqual({
        type: 'SideMenuRoot',
        children: [
          {
            type: 'SideMenuCenter',
            children: [
              {
                type: 'ContainerStack',
                children: [
                  {
                    type: 'Container',
                    data: {
                      name: 'com.example.MyScreen'
                    },
                    children: []
                  }
                ]
              }
            ]
          },
          {
            type: 'SideMenuRight',
            children: [
              {
                type: 'Container',
                data: {
                  name: 'com.example.SideMenu'
                },
                children: []
              }
            ]
          }
        ]
      });
  });

  it('parses both side menus', () => {
    expect(uut.parseFromSimpleJSON(SimpleLayouts.singleWithBothMenus))
      .toEqual({
        type: 'SideMenuRoot',
        children: [
          {
            type: 'SideMenuLeft',
            children: [
              {
                type: 'Container',
                data: {
                  name: 'com.example.Menu1'
                },
                children: []
              }
            ]
          },
          {
            type: 'SideMenuCenter',
            children: [
              {
                type: 'ContainerStack',
                children: [
                  {
                    type: 'Container',
                    data: {
                      name: 'com.example.MyScreen'
                    },
                    children: []
                  }
                ]
              }
            ]
          },
          {
            type: 'SideMenuRight',
            children: [
              {
                type: 'Container',
                data: {
                  name: 'com.example.Menu2'
                },
                children: []
              }
            ]
          }
        ]
      });
  });

  it('parses tabs with side menus', () => {
    expect(uut.parseFromSimpleJSON(SimpleLayouts.tabBasedWithBothSideMenus))
      .toEqual({
        type: 'SideMenuRoot',
        children: [
          {
            type: 'SideMenuLeft',
            children: [
              {
                type: 'Container',
                data: {
                  name: 'com.example.Menu1'
                },
                children: []
              }
            ]
          },
          {
            type: 'SideMenuCenter',
            children: [
              {
                type: 'Tabs',
                children: [
                  {
                    type: 'ContainerStack',
                    children: [
                      {
                        type: 'Container',
                        data: {
                          name: 'com.example.FirstTab'
                        },
                        children: []
                      }
                    ]
                  },
                  {
                    type: 'ContainerStack',
                    children: [
                      {
                        type: 'Container',
                        data: {
                          name: 'com.example.SecondTab'
                        },
                        children: []
                      }
                    ]
                  }
                ]
              }
            ]
          },
          {
            type: 'SideMenuRight',
            children: [
              {
                type: 'Container',
                data: {
                  name: 'com.example.Menu2'
                },
                children: []
              }
            ]
          }
        ]
      });
  });
});
