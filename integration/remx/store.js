import _ from 'lodash';
import * as remx from 'remx';

export const state = remx.state({
  person: {
    name: 'no name'
  }
});

export const mutators = remx.setters({
  setName(newName) {
    state.person.name = newName;
  },

  setAge(age) {
    state.merge({ person: { age } });
  }
});

export const selectors = remx.getters({
  getName() {
    return _.get(state, ['person', 'name']);
  },

  getAge() {
    return _(state).get('person.age');
  }
});
