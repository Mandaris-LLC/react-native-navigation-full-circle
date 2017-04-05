const exec = require('shell-utils').exec;

function runWithXcpretty(cmd) {
  if (exec.which(`xcpretty`)) {
    exec.execSync(`${cmd} | xcpretty && exit \${PIPESTATUS[0]}`);
  } else {
    exec.execSync(`${cmd}`);
  }
}

function run() {
  runWithXcpretty(`RCT_NO_LAUNCH_PACKAGER=true
          cd ./playground/ios && xcodebuild
            build build-for-testing
            -scheme "ReactNativeNavigation"
            -project playground.xcodeproj
            -sdk iphonesimulator
            -configuration Debug
            -derivedDataPath ./DerivedData/playground`);
  runWithXcpretty(`RCT_NO_LAUNCH_PACKAGER=true
          cd ./playground/ios && xcodebuild
            test-without-building
            -scheme "ReactNativeNavigation"
            -project playground.xcodeproj
            -sdk iphonesimulator
            -destination 'platform=iOS Simulator,name=iPhone 7'
            -derivedDataPath ./DerivedData/playground`);
}

run();
