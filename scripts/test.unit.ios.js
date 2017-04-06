const _ = require('lodash');
const exec = require('shell-utils').exec;

const release = _.includes(process.argv, 'release');

function runWithXcpretty(cmd) {
  if (exec.which(`xcpretty`)) {
    exec.execSync(`${cmd} | xcpretty && exit \${PIPESTATUS[0]}`);
  } else {
    exec.execSync(`${cmd}`);
  }
}

function run() {
  const conf = release ? `Release` : `Debug`;
  runWithXcpretty(`RCT_NO_LAUNCH_PACKAGER=true
          cd ./playground/ios && xcodebuild
            build build-for-testing
            -scheme "ReactNativeNavigation"
            -project playground.xcodeproj
            -sdk iphonesimulator
            -configuration ${conf}
            -derivedDataPath ./DerivedData/playground
            ONLY_ACTIVE_ARCH=YES`);
  runWithXcpretty(`RCT_NO_LAUNCH_PACKAGER=true
          cd ./playground/ios && xcodebuild
            test-without-building
            -scheme "ReactNativeNavigation"
            -project playground.xcodeproj
            -sdk iphonesimulator
            -configuration ${conf}
            -destination 'platform=iOS Simulator,name=iPhone 7'
            -derivedDataPath ./DerivedData/playground
            ONLY_ACTIVE_ARCH=YES`);
}

run();
