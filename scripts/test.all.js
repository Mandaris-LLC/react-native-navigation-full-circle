const shellUtils = require('shell-utils');

function run() {
  // shellUtils.exec.execSync(`yarn run test-js`);
  // shellUtils.exec.execSync(`yarn run test-unit-android && yarn run test-unit-ios`);
  shellUtils.exec.exec(`yarn run test-e2e-android`);
  shellUtils.exec.exec(`yarn run test-e2e-ios`);
}

run();
