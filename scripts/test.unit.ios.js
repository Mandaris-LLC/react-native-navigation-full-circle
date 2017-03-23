const shellUtils = require('shell-utils');

function runWithXcprettyIfPossible(cmd) {
  if (hasXcpretty()) {
    shellUtils.exec.execSync(`${cmd} | xcpretty && exit \${PIPESTATUS[0]}`);
  } else {
    shellUtils.exec.execSync(`${cmd}`);
  }
}

function hasXcpretty() {
  try {
    return shellUtils.exec.execSyncRead(`which xcpretty`);
  } catch (e) {
    return false;
  }
}

function run() {
  try {
    shellUtils.exec.execSync(`echo 'travis_fold:start:xcodeunit'`);
    runWithXcprettyIfPossible(`RCT_NO_LAUNCH_PACKAGER=true
          cd ./playground/ios && xcodebuild
            build build-for-testing
            -scheme "playground"
            -project playground.xcodeproj
            -sdk iphonesimulator
            -configuration Debug`);
    runWithXcprettyIfPossible(`RCT_NO_LAUNCH_PACKAGER=true
          cd ./playground/ios && xcodebuild
            test-without-building
            -scheme "playground"
            -project playground.xcodeproj
            -destination 'platform=iOS Simulator,name=iPhone 7'`);
  } finally {
    shellUtils.exec.execSync(`echo 'travis_fold:end:xcodeunit'`);
  }
}

run();
