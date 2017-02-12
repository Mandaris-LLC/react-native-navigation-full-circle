import * as SimpleLayouts from './SimpleLayouts';
import LayoutTreeParser from './LayoutTreeParser';
import LayoutTreeCrawler from './LayoutTreeCrawler';
import Store from '../containers/Store';
import UniqueIdProvider from '../adapters/UniqueIdProvider.mock';
import NativeCommandsSender from '../adapters/NativeCommandsSender.mock';

import AppCommands from './AppCommands';

describe('AppCommands', () => {
  let uut;
  let mockCommandsSender;
  let store;

  beforeEach(() => {
    mockCommandsSender = new NativeCommandsSender();
    store = new Store();

    uut = new AppCommands(mockCommandsSender, new LayoutTreeParser(), new LayoutTreeCrawler(new UniqueIdProvider(), store));
  });

  describe('setRoot', () => {
    it('sends setRoot to native after parsing into a correct layout tree', () => {
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

    it('deep clones input to avoid mutation errors', () => {
      const obj = {};
      uut.setRoot({ container: { inner: obj } });
      expect(mockCommandsSender.setRoot.mock.calls[0][0].children[0].data.inner).not.toBe(obj);
    });

    it('passProps into containers', () => {
      expect(store.getPropsForContainerId('Container+UNIQUE_ID')).toEqual({});
      uut.setRoot(SimpleLayouts.singleScreenWithAditionalParams);
      expect(store.getPropsForContainerId('Container+UNIQUE_ID')).toEqual(SimpleLayouts.passProps);
    });

    it('returns a promise with the resolved layout', async () => {
      mockCommandsSender.setRoot.mockReturnValue(Promise.resolve('the resolved layout'));
      const result = await uut.setRoot({ container: { name: 'com.example.MyScreen' } });
      expect(result).toEqual('the resolved layout');
    });
  });


  describe('showModal', () => {
    it('sends command to native after parsing into a correct layout tree', () => {
      uut.showModal({
        name: 'com.example.MyScreen'
      });
      expect(mockCommandsSender.showModal).toHaveBeenCalledTimes(1);
      expect(mockCommandsSender.showModal).toHaveBeenCalledWith({
        type: 'Container',
        id: 'Container+UNIQUE_ID',
        children: [],
        data: {
          name: 'com.example.MyScreen'
        }
      });
    });

    it('deep clones input to avoid mutation errors', () => {
      const obj = {};
      uut.showModal({ inner: obj });
      expect(mockCommandsSender.showModal.mock.calls[0][0].data.inner).not.toBe(obj);
    });

    it('passProps into containers', () => {
      expect(store.getPropsForContainerId('Container+UNIQUE_ID')).toEqual({});
      uut.showModal({
        name: 'com.example.MyScreen',
        passProps: SimpleLayouts.passProps
      });
      expect(store.getPropsForContainerId('Container+UNIQUE_ID')).toEqual(SimpleLayouts.passProps);
    });

    it('returns a promise with the resolved layout', async () => {
      mockCommandsSender.showModal.mockReturnValue(Promise.resolve('the resolved layout'));
      const result = await uut.showModal({ container: { name: 'com.example.MyScreen' } });
      expect(result).toEqual('the resolved layout');
    });
  });
});
