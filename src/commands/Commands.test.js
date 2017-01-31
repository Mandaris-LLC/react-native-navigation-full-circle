import * as SimpleLayouts from './SimpleLayouts';

describe('Commands', () => {
  let uut;
  let mockCommandsSender;
  let store;

  beforeEach(() => {
    const NativeCommandsSender = jest.genMockFromModule('../adapters/NativeCommandsSender').default;
    mockCommandsSender = new NativeCommandsSender();
    const mockIdProvider = { generate: (prefix) => `${prefix}+UNIQUE_ID` };
    const Store = require('../containers/Store').default;
    store = new Store();

    const Commands = require('./Commands').default;
    uut = new Commands(mockCommandsSender, mockIdProvider, store);
  });

  describe('setRoot', () => {
    it('sends setRoot to native after parsing into layoutTree', () => {
      uut.setRoot({
        container: {
          name: 'com.example.MyScreen'
        }
      });
      expect(mockCommandsSender.setRoot).toHaveBeenCalledTimes(1);
      expect(mockCommandsSender.setRoot).toHaveBeenCalledWith({
        type: 'ContainerStack',
        id: 'ContainerStack+UNIQUE_ID',
        data: {},
        children: [
          {
            type: 'Container',
            id: 'Container+UNIQUE_ID',
            children: [],
            data: {
              name: 'com.example.MyScreen'
            }
          }
        ]
      });
    });

    it('passProps into containers', () => {
      expect(store.getPropsForContainerId('Container+UNIQUE_ID')).toEqual({});
      uut.setRoot(SimpleLayouts.singleScreenWithAditionalParams);
      expect(store.getPropsForContainerId('Container+UNIQUE_ID')).toEqual(SimpleLayouts.passProps);
    });
  });
});
