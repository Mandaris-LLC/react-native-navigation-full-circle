import _ from 'lodash';

const state = {
  propsByContainerId: {},
  containersByKey: {}
};

export function setPropsForContainerId(containerId, props) {
  _.set(state.propsByContainerId, containerId, props);
}

export function getPropsForContainerId(containerId) {
  return _.get(state.propsByContainerId, containerId, {});
}

export function setContainerClass(containerKey, ContainerClass) {
  state.containersByKey[containerKey] = ContainerClass;
}

export function getContainerClass(containerKey) {
  return state.containersByKey[containerKey];
}
