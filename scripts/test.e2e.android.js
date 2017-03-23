const exec = require('shell-utils').exec;

function run() {
  if (process.env.CI) {
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

run();
