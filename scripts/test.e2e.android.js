/*eslint-disable no-console*/
const _ = require('lodash');
const exec = require('shell-utils').exec;

const release = _.includes(process.argv, 'release');

run();

function run() {
  if (process.env.CI) {
    try {
      launchEmulator();
      runTests();
    } finally {
      killEmulators();
    }
  } else {
    runTests();
  }
}

function runTests() {
  exec.execSync(`yarn run uninstall-android`);
  exec.execSync(`yarn run install-android ${release ? '-- release' : ''}`);
  exec.execSync(`cd AndroidE2E && ./gradlew connectedDebugAndroidTest`);
}

function launchEmulator() {
  console.log(`Launching Android Emulator`);
  exec.execSync(`cd $ANDROID_HOME/tools && ./emulator -skin 1080x1920 -no-audio @pixel`);
  exec.execSync(`./scripts/waitForAndroidEmulator.sh`);
}

function killEmulators() {
  exec.execSync(`adb devices | grep emulator | cut -f1 | while read line; do adb -s $line emu kill; done`);
}
