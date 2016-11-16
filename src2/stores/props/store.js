import _ from 'lodash';

const state = {
  propsByScreenId: {}
};

export const mutators = {
  setPropsForScreenId(screenId, props) {
    _.set(state.propsByScreenId, screenId, props);
  }
};

export const selectors = {
  getPropsForScreenId(screenId) {
    return _.get(state.propsByScreenId, screenId, {});
  }
};
