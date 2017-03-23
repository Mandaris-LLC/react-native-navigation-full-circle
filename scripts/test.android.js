const exec = require('shell-utils').exec;

function unitTests() {
  exec.execSync(`cd lib/android && ./gradlew clean testDebugUnitTest`);
}

function e2e() {
  if (!process.env.CI) {
    console.log(`e2e disabled on CI`); //eslint-disable-line
    return;
  }

  try {
    exec.execSync(`echo 'travis_fold:start:android-e2e'`);
    exec.execSync(`yarn run uninstall-android`);
    exec.execSync(`yarn run install-android`);
    exec.execSync(`cd AndroidE2E && ./gradlew connectedDebugAndroidTest`);
  } finally {
    exec.execSync(`echo 'travis_fold:end:android-e2e'`);
  }
}

function run() {
  if (process.env.CI) {
    exec.execSync(`./scripts/installAndroidSDK.sh`);
  }
  unitTests();
  e2e();
}

run();
