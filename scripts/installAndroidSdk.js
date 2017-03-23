const exec = require('shell-utils').exec;

run();

function run() {
  console.log(`Downloading Android SDK`); //eslint-disable-line
  // fix for https://code.google.com/p/android/issues/detail?id=223424
  exec.execSync(`mkdir -p ~/.android`);
  exec.execSync(`curl --location https://dl.google.com/android/android-sdk_r24.4.1-macosx.zip | tar -x -z -C $HOME`);
  console.log(`Copying Android licenses`); //eslint-disable-line
  exec.execSync(`mkdir -p "${ANDROID_HOME}"/licenses`);
  exec.execSync(`cp "${__dirname}"/android-sdk-licenses/* "${ANDROID_HOME}"/licenses`);
}
