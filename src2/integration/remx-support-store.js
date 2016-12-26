import _ from 'lodash';
import * as remx from 'remx';

export const state = remx.state({
  person: {}
});

export const mutators = remx.setters({
  setName(newName) {
    state.person.name = newName;
  }
});

export const selectors = remx.getters({
  getName() {
    return _(state).get('person.name', 'no name');
  }
});
