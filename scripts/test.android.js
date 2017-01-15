const exec = require('shell-utils').exec;

function run() {
  if (process.env.TRAVIS) {
    exec.execSync(`./scripts/installAndroidSDK.sh`);
  }
  exec.execSync(`cd android && ./gradlew clean testDebugUnitTest`);
}

run();
