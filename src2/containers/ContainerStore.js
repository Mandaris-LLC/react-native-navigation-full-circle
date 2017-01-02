const state = {
  containersByKey: {}
};

export function setContainerClass(containerKey, ContainerClass) {
  state.containersByKey[containerKey] = ContainerClass;
}

export function getContainerClass(containerKey) {
  return state.containersByKey[containerKey];
}
