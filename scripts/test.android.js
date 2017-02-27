const exec = require('shell-utils').exec;

function run() {
  if (process.env.CI) {
    exec.execSync(`./scripts/installAndroidSDK.sh`);
  }

  exec.execSync(`cd android && ./gradlew clean testDebugUnitTest`);
}

run();
