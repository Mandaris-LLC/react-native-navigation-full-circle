import { NativeModules } from 'react-native';
import NativeCommandsSender from './NativeCommandsSender';

describe('NativeCommandsSender', () => {
  let uut, mockNativeModule;

  beforeEach(() => {
    mockNativeModule = {
      setRoot: jest.fn(),
      push: jest.fn(),
      pop: jest.fn(),
      showModal: jest.fn(),
      dismissModal: jest.fn()
    };
    NativeModules.RNNBridgeModule = mockNativeModule;
    uut = new NativeCommandsSender();
  });

  it('delegates to native', () => {
    uut.setRoot();
    expect(mockNativeModule.setRoot).toHaveBeenCalledTimes(1);
  });

  it('returns promise with resolved layout', async () => {
    const result = await uut.setRoot({});
    expect(result).toBeDefined();
  });

  it('push sends to native and resolves with promise', async () => {
    const theNewContainer = {};
    const result = await uut.push('theContainerId', theNewContainer);
    expect(mockNativeModule.push).toHaveBeenCalledTimes(1);
    expect(result).toBeDefined();
  });

  it('pop sends to native with containerId', async () => {
    const result = await uut.pop('theContainerId');
    expect(mockNativeModule.pop).toHaveBeenCalledTimes(1);
    expect(result).toBeDefined();
  });

  it('showModal sends to native', async () => {
    const result = await uut.showModal({});
    expect(mockNativeModule.showModal).toHaveBeenCalledTimes(1);
    expect(result).toBeDefined();
  });

  it('dismissModal', async () => {
    const result = await uut.dismissModal('id');
    expect(mockNativeModule.dismissModal).toHaveBeenCalledTimes(1);
    expect(result).toEqual('id');
  });
});
