const _ = require('lodash');
const shellUtils = require('shell-utils');
const fs = require('fs');

function runEmulator() {
  //   shellUtils.exec.execSync(`echo no | android create avd --force -n test -t android-25 --abi armeabi-v7a`);
  //   shellUtils.exec.execSync(`emulator -avd test -no-skin -no-audio -no-window &`);
  //   shellUtils.exec.execSync(`android-wait-for-emulator`);
  //   shellUtils.exec.execSync(`adb shell input keyevent 82 &`);
}

function e2e() {
  shellUtils.exec.execSync(`yarn run uninstall-android`);
  shellUtils.exec.execSync(`yarn run android`);
  shellUtils.exec.execSync(`cd ../AndroidE2e/ && ./gradlew connectedDebugAndroidTest`);
}

function run() {
  shellUtils.exec.execSync(`echo 'travis_fold:start:android-e2e'`);
  try {
    e2e();
  } finally {
    shellUtils.exec.execSync(`echo 'travis_fold:end:android-e2e'`);
  }
}

run();
