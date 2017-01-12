import _ from 'lodash';

const state = {
  propsByContainerId: {},
  containersByName: {}
};

export function setPropsForContainerId(containerId, props) {
  _.set(state.propsByContainerId, containerId, props);
}

export function getPropsForContainerId(containerId) {
  return _.get(state.propsByContainerId, containerId, {});
}

export function setContainerClass(containerName, ContainerClass) {
  state.containersByName[containerName] = ContainerClass;
}

export function getContainerClass(containerName) {
  return state.containersByName[containerName];
}
