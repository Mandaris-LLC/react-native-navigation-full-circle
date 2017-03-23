const exec = require('shell-utils').exec;

function e2e() {
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

  exec.execSync(`cd lib/android && ./gradlew clean testDebugUnitTest`);

  if (!process.env.CI) {
    e2e();
  }
}

run();
