import _ from 'lodash';

const propsByScreenId = {};

export function setPropsForScreenId(screenId, props) {
  _.set(propsByScreenId, screenId, props);
}

export function getPropsForScreenId(screenId) {
  return _.get(propsByScreenId, screenId, {});
}
