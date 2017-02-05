import _ from 'lodash';

export default class PrivateEventsListener {
  constructor(nativeEventsReceiver, store) {
    this.nativeEventsReceiver = nativeEventsReceiver;
    this.store = store;
  }

  listenAndHandlePrivateEvents() {
    this.nativeEventsReceiver.containerStart(this._handleContainerStart);
  }

  _handleContainerStart(params) {
    // const id = params.id;
    // try {
    //   const ref = this.store.getRefForId(id);
    //   if (ref && ref.onStart) {
    //     ref.onStart();
    //   }
    // } catch (e) {
    //   console.warn(e);
    // }
  }
}
