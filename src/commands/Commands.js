import _ from 'lodash';

export function startApp(params) {
  validateParams(params);
}

function validateParams(params) {
  const msg = `invalid params passed to startApp: ${params}`;
  if (!params) {
    throw new Error(msg);
  }
  if (params.containerKey) {
    return true;
  }
  if (params.tabs && params.tabs.length > 0 && _.every(params.tabs, (t) => t.containerKey)) {
    return true;
  }
  throw new Error(msg);
}
