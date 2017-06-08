/*eslint-disable no-console*/
const _ = require('lodash');
const exec = require('shell-utils').exec;

const release = _.includes(process.argv, 'release');

run();

function run() {
  if (process.env.CI) {
    try {
      // console.log(`android e2e disabled on CI for now`);
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
  exec.exec(`cd $ANDROID_HOME/tools && ./emulator -skin 1080x1920 -gpu host -no-audio @pixel`);
  exec.execSync(`./scripts/waitForAndroidEmulator.sh`);
}

function killEmulators() {
  exec.execSync(`adb devices | grep emulator | cut -f1 | while read line; do adb -s $line emu kill; done`);
}
