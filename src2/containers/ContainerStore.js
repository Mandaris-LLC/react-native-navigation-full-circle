const state = {
  containersByKey: {}
};

export function saveContainerClass(containerKey, containerClass) {
  state.containersByKey[containerKey] = containerClass;
}

export function getContainerClass(containerKey) {
  return state.containersByKey[containerKey];
}
