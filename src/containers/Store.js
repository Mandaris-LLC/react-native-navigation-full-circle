import _ from 'lodash';

export default class Store {
  constructor() {
    this.propsByContainerId = {};
    this.containersByName = {};
    this.refsById = {};
  }

  setPropsForContainerId(containerId, props) {
    _.set(this.propsByContainerId, containerId, props);
  }

  getPropsForContainerId(containerId) {
    return _.get(this.propsByContainerId, containerId, {});
  }

  setContainerClassForName(containerName, ContainerClass) {
    _.set(this.containersByName, containerName, ContainerClass);
  }

  getContainerClassForName(containerName) {
    return _.get(this.containersByName, containerName);
  }

  setRefForId(id, ref) {
    _.set(this.refsById, id, ref);
  }

  getRefForId(id) {
    return _.get(this.refsById, id);
  }

  cleanId(id) {
    _.unset(this.refsById, id);
    _.unset(this.propsByContainerId, id);
  }
}
