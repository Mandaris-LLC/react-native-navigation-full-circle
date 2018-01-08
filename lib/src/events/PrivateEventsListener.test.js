const PrivateEventsListener = require('./PrivateEventsListener');
const NativeEventsReceiver = require('../adapters/NativeEventsReceiver.mock');
const Store = require('../components/Store');

describe('PrivateEventsListener', () => {
  let uut;
  let nativeEventsReceiver;
  let store;

  beforeEach(() => {
    nativeEventsReceiver = new NativeEventsReceiver();
    store = new Store();
    uut = new PrivateEventsListener(nativeEventsReceiver, store);
  });

  it('register and handle componentDidAppear', () => {
    const mockRef = {
      didAppear: jest.fn()
    };
    store.setRefForComponentId('myComponentId', mockRef);
    uut.listenAndHandlePrivateEvents();
    expect(nativeEventsReceiver.componentDidAppear).toHaveBeenCalledTimes(1);
    const callbackFunction = nativeEventsReceiver.componentDidAppear.mock.calls[0][0];
    expect(callbackFunction).toBeInstanceOf(Function);

    expect(mockRef.didAppear).not.toHaveBeenCalled();
    callbackFunction('myComponentId');

    expect(mockRef.didAppear).toHaveBeenCalledTimes(1);
  });

  it('register and listen componentDidDisappear', () => {
    uut.listenAndHandlePrivateEvents();
    expect(nativeEventsReceiver.componentDidDisappear).toHaveBeenCalledTimes(1);
  });

  it('register and handle onNavigationButtonPressed', () => {
    const mockRef = {
      onNavigationButtonPressed: jest.fn()
    };
    store.setRefForComponentId('myComponentId', mockRef);
    uut.listenAndHandlePrivateEvents();
    expect(nativeEventsReceiver.navigationButtonPressed).toHaveBeenCalledTimes(1);
    const callbackFunction = nativeEventsReceiver.navigationButtonPressed.mock.calls[0][0];
    expect(callbackFunction).toBeInstanceOf(Function);

    expect(mockRef.onNavigationButtonPressed).not.toHaveBeenCalled();
    callbackFunction({ componentId: 'myComponentId', buttonId: 'myButtonId' });

    expect(mockRef.onNavigationButtonPressed).toHaveBeenCalledTimes(1);
  });
});
