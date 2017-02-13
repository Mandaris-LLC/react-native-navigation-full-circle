const _ = require('lodash');
const shellUtils = require('shell-utils');
const fs = require('fs');

const release = _.includes(process.argv, 'release');

function e2e() { //eslint-disable-line
  try {
    shellUtils.exec.execSync(`echo 'travis_fold:start:android-espresso'`);
    shellUtils.exec.execSync(`cd android && ./gradlew uninstallAll`);
    shellUtils.exec.execSync(`cd android && ./gradlew connectedDebugAndroidTest`);
  } finally {
    shellUtils.exec.execSync(`echo 'travis_fold:end:android-espresso'`);
  }
}

function run() {
  e2e();
}

run();
