const exec = require('shell-utils').exec;

function run() {
  if (process.env.CI) {
    console.log(`e2e disabled on CI`); //eslint-disable-line
    return;
  }

  exec.execSync(`yarn run uninstall-android`);
  exec.execSync(`yarn run install-android`);
  exec.execSync(`cd AndroidE2E && ./gradlew connectedDebugAndroidTest`);
}

run();
