import _ from 'lodash';

const propsByContainerId = {};

export function setPropsForContainerId(containerId, props) {
  _.set(propsByContainerId, containerId, props);
}

export function getPropsForContainerId(containerId) {
  return _.get(propsByContainerId, containerId, {});
}
