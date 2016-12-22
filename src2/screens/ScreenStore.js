const state = {
  screensByKey: {}
};

export function saveScreenClass(screenKey, ScreenClass) {
  state.screensByKey[screenKey] = ScreenClass;
}

export function getScreenClass(screenKey) {
  return state.screensByKey[screenKey];
}
