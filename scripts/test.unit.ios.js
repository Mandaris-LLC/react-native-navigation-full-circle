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
            -scheme "playground"
            -project playground.xcodeproj
            -sdk iphonesimulator
            -configuration Debug`);
  runWithXcpretty(`RCT_NO_LAUNCH_PACKAGER=true
          cd ./playground/ios && xcodebuild
            test-without-building
            -scheme "playground"
            -project playground.xcodeproj
            -destination 'platform=iOS Simulator,name=iPhone 7'`);
}

run();
