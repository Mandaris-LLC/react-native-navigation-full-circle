import _ from 'lodash';

export default class Store {
  constructor() {
    this.propsByContainerId = {};
    this.containersByName = {};
  }

  setPropsForContainerId(containerId, props) {
    _.set(this.propsByContainerId, containerId, props);
  }

  getPropsForContainerId(containerId) {
    return _.get(this.propsByContainerId, containerId, {});
  }

  setContainerClass(containerName, ContainerClass) {
    this.containersByName[containerName] = ContainerClass;
  }

  getContainerClass(containerName) {
    return this.containersByName[containerName];
  }
}
