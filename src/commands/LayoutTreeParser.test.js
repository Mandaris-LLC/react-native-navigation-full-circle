import * as SimpleLayouts from './SimpleLayouts';

describe('LayoutTreeParser', () => {
  let uut;

  beforeEach(() => {
    const uniqueIdProvider = { generate: (prefix) => `${prefix}+UNIQUE_ID` };
    const LayoutTreeParser = require('./LayoutTreeParser').default;
    uut = new LayoutTreeParser(uniqueIdProvider);
  });

  it('adds uniqueId to containers', () => {
    const input = { container: {} };
    expect(uut.parseFromSimpleJSON(input))
      .toEqual({
        type: 'ContainerStack',
        id: 'ContainerStack+UNIQUE_ID',
        children: [
          {
            type: 'Container',
            id: 'Container+UNIQUE_ID',
            data: {},
            children: []
          }
        ]
      });
  });

  it('parses single screen', () => {
    expect(uut.parseFromSimpleJSON(SimpleLayouts.singleScreenApp))
      .toEqual({
        type: 'ContainerStack',
        id: 'ContainerStack+UNIQUE_ID',
        children: [
          {
            type: 'Container',
            id: 'Container+UNIQUE_ID',
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
        id: 'ContainerStack+UNIQUE_ID',
        children: [
          {
            type: 'Container',
            id: 'Container+UNIQUE_ID',
            children: [],
            data: {
              name: 'com.example.MyScreen',
              passProps: {
                foo: {
                  number: 1,
                  string: 'Hello!'
                },
                bar: SimpleLayouts.passedFunction
              },
              style: {},
              buttons: {}
            }
          }
        ]
      });
    expect(uut.parseFromSimpleJSON(SimpleLayouts.singleScreenWithAditionalParams).children[0].data.passProps.bar()).toEqual('Hello from a function');
  });

  it('parses tab based', () => {
    expect(uut.parseFromSimpleJSON(SimpleLayouts.tabBasedApp))
      .toEqual({
        type: 'Tabs',
        id: 'Tabs+UNIQUE_ID',
        children: [
          {
            type: 'ContainerStack',
            id: 'ContainerStack+UNIQUE_ID',
            children: [
              {
                type: 'Container',
                id: 'Container+UNIQUE_ID',
                children: [],
                data: {
                  name: 'com.example.ATab'
                }
              }
            ]
          },
          {
            type: 'ContainerStack',
            id: 'ContainerStack+UNIQUE_ID',
            children: [
              {
                type: 'Container',
                id: 'Container+UNIQUE_ID',
                children: [],
                data: {
                  name: 'com.example.SecondTab'
                }
              }
            ]
          },
          {
            type: 'ContainerStack',
            id: 'ContainerStack+UNIQUE_ID',
            children: [
              {
                type: 'Container',
                id: 'Container+UNIQUE_ID',
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
        id: 'SideMenuRoot+UNIQUE_ID',
        children: [
          {
            type: 'SideMenuLeft',
            id: 'SideMenuLeft+UNIQUE_ID',
            children: [
              {
                type: 'Container',
                id: 'Container+UNIQUE_ID',
                data: {
                  name: 'com.example.SideMenu'
                },
                children: []
              }
            ]
          },
          {
            type: 'SideMenuCenter',
            id: 'SideMenuCenter+UNIQUE_ID',
            children: [
              {
                type: 'ContainerStack',
                id: 'ContainerStack+UNIQUE_ID',
                children: [
                  {
                    type: 'Container',
                    id: 'Container+UNIQUE_ID',
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
});
