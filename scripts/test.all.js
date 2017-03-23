const exec = require('shell-utils').exec;

function run() {
  exec.execSync(`yarn run test-js`);
  exec.execSync(`yarn run test-unit-android && yarn run test-unit-ios`);
  exec.exec(`yarn run test-e2e-android`);
  exec.exec(`yarn run test-e2e-ios`);
}

run();
