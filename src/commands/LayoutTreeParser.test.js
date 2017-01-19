import * as SimpleLayouts from './SimpleLayouts';

describe('LayoutTreeParser', () => {
  let uut;

  beforeEach(() => {
    const uniqueIdProvider = {generate: (prefix) => `${prefix}+UNIQUE_ID`};
    const LayoutTreeParser = require('./LayoutTreeParser').default;
    uut = new LayoutTreeParser(uniqueIdProvider);
  });

  it('parses single screen', () => {
    expect(uut.parseSimpleJSON(SimpleLayouts.singleScreenApp))
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
    expect(uut.parseSimpleJSON(SimpleLayouts.singleScreenWithAditionalParams))
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
  });

  xit('parses tab based', () => {
    expect(uut.parseSimpleJSON(SimpleLayouts.tabBasedApp))
      .toEqual({
        type: 'Tabs',
        id: 'Tabs+UNIQUE_ID',
        children: [
          {
            container: {
              name: 'com.example.FirstTab'
            }
          },
          {
            container: {
              name: 'com.example.SecondTab'
            }
          },
          {
            container: {
              name: 'com.example.FirstTab'
            }
          }
        ]
      });
  });

  xit('adds uniqueId to containers', () => {
    const input = {container: {}};
    expect(uut.parse(input)).toEqual({container: {id: 'Container+UNIQUE_ID'}});
  });

  xit('parses side menus', () => {
    //const result2 = {
    //  type: 'Menus',
    //  id: 'MenusUNIQUE_ID',
    //  children: [
    //    {
    //      type: 'Container',
    //      id: 'ContainerUNIQUE_ID',
    //      name: 'com.example.LeftSideMenu',
    //      children: []
    //    },
    //    {
    //      type: 'ContainerStack',
    //      id: 'ContainerStackUNIQUE_ID',
    //      children: [
    //        {
    //          type: 'Container',
    //          id: 'ContainerUNIQUE_ID',
    //          name: 'com.example.WelcomeScreen',
    //          children: []
    //        }
    //      ]
    //    },
    //    {
    //      type: 'Container',
    //      id: 'ContainerUNIQUE_ID',
    //      name: 'com.example.RightSideMenu',
    //      children: []
    //    }
    //  ]
    //};
  });
});
