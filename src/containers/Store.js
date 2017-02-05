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
    this.containersByName[containerName] = ContainerClass;
  }

  getContainerClassForName(containerName) {
    return this.containersByName[containerName];
  }

  setRefForId(id, ref) {
    this.refsById[id] = ref;
  }

  getRefForId(id) {
    return this.refsById[id];
  }

  cleanId(id) {
    this.refsById[id] = undefined;
    this.propsByContainerId[id] = undefined;
  }
}
