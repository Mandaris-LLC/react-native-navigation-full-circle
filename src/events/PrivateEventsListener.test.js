import _ from 'lodash';
import PrivateEventsListener from './PrivateEventsListener';
import NativeEventsReceiver from '../adapters/NativeEventsReceiver.mock';

describe('PrivateEventsListener', () => {
  let uut;
  let nativeEventsReceiver;

  beforeEach(() => {
    nativeEventsReceiver = new NativeEventsReceiver();
    uut = new PrivateEventsListener(nativeEventsReceiver);
  });

  it('registers for private events against nativeEventsReceiver', () => {
    uut.listenAndHandlePrivateEvents();
    expect(nativeEventsReceiver.containerStart).toHaveBeenCalledTimes(1);
    const callbackFunction = nativeEventsReceiver.containerStart.mock.calls[0][0];
    expect(callbackFunction).toBeInstanceOf(Function);
    callbackFunction('myContainerId');
  });
});
