const exec = require('shell-utils').exec;

function travisRun(cmd) {
  exec.execSync(`echo "travis_fold:start:${cmd}"`);
  exec.execSync(cmd);
  exec.execSync(`echo "travis_fold:end:${cmd}"`);
}

function run() {
  travisRun(`yarn install`);
  travisRun(`yarn run lint`);
  travisRun(`yarn run test-js`);
  travisRun(`yarn run test-android`);
  travisRun(`yarn run test-ios`);
  travisRun(`yarn run release`);
}

run();
