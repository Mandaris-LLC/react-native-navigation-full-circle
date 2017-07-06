
const exec = require('shell-utils').exec;

async function run() {
  exec.execSync(`yarn run clean`);
  exec.execSync(`yarn run test-js & yarn run test-unit-android -- release & yarn run test-unit-ios -- release`);
  await Promise.all([exec.execAsync(`yarn run test-e2e-android -- release`), exec.execAsync(`yarn run test-e2e-ios -- release`)]);
  console.log('DONE!!!'); //eslint-disable-line
}

run();
