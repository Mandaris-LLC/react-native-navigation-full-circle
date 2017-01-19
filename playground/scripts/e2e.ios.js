const _ = require('lodash');
const shellUtils = require('shell-utils');
const fs = require('fs');

const release = _.includes(process.argv, 'release');

function buildProjForDetox() {
  const scheme = release ? `playground_release` : `playground`;

  shellUtils.exec.execSync(`echo 'travis_fold:start:xcodebuild'`);
  const cmd = `RCT_NO_LAUNCH_PACKAGER=true
          cd ios && xcodebuild
            -scheme ${scheme}
            ${release ? 'clean build' : 'build'}
            -project playground.xcodeproj
            -sdk iphonesimulator
            -derivedDataPath ./DerivedData/playground`;

  if (hasXcpretty()) {
    shellUtils.exec.execSync(`${cmd} | xcpretty && exit \${PIPESTATUS[0]}`);
  } else {
    shellUtils.exec.execSync(`${cmd}`);
  }
  shellUtils.exec.execSync(`echo 'travis_fold:end:xcodebuild'`);
}
function hasXcpretty() {
  try {
    return shellUtils.exec.execSyncRead(`which xcpretty`);
  } catch (e) {
    return false;
  }
}

function e2e() { //eslint-disable-line
  try {
    shellUtils.exec.execSync(`echo 'travis_fold:start:detox-ios'`);
    shellUtils.exec.execSyncSilent(`watchman watch-del-all || true`);
    shellUtils.exec.kill(`detox-server`);
    shellUtils.exec.exec(`./node_modules/.bin/detox-server > ./detox-server.log 2>&1`);
    const detoxAppBuildPath = `ios/DerivedData/playground/Build/Products/${release ? 'Release' : 'Debug'}-iphonesimulator/playground.app`;

    shellUtils.exec.execSync(`detoxAppBuildPath="${detoxAppBuildPath}"
                              BABEL_ENV=test
                              ./node_modules/mocha/bin/mocha e2e
                                --timeout ${10 * 60 * 1000}
                                --recursive
                                --compilers js:babel-register`);
  } finally {
    shellUtils.exec.kill(`detox-server`);
    if (process.env.CI) {
      shellUtils.exec.kill(`Simulator`);
      shellUtils.exec.kill(`CoreSimulator`);
    }
    shellUtils.exec.execSync(`cat ./detox-server.log`);
    shellUtils.exec.execSync(`rm -f ./detox-server.log`);
    shellUtils.exec.execSync(`sleep 5`);
    shellUtils.exec.execSync(`echo 'travis_fold:end:detox-ios'`);
  }
}

function run() {
  buildProjForDetox();
  e2e();
}

run();
