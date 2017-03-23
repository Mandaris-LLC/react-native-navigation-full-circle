const exec = require('shell-utils').exec;

function run() {
  if (process.env.CI) {
    exec.execSync(`./scripts/installAndroidSDK.sh`);
  }

  exec.execSync(`cd lib/android && ./gradlew clean testDebugUnitTest`);

  if (!process.env.CI) {
    // exec.execSync(`cd playground && yarn run e2e-android`);
  }
}

run();
