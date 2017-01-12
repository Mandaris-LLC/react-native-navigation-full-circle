const _ = require('lodash');
const exec = require('./exec');
const fs = require('fs');

const release = _.includes(process.argv, 'release');

function hackReactXcodeScript() {
  const fileToHack = `./node_modules/react-native/packager/react-native-xcode.sh`;
  const lines = _.split(String(fs.readFileSync(fileToHack)), '\n');
  lines[11] = release ? '' : `exit 0;`;
  fs.writeFileSync(fileToHack, _.join(lines, '\n'));
}

function buildProjForDetox() {
  const scheme = release ? `playground_release_Detox` : `playground_Detox`;
  const args = release ? '' : `GCC_PREPROCESSOR_DEFINITIONS="DEBUG=1 RCT_DEBUG=1 RCT_DEV=1 RCT_NSASSERT=1"`;

  exec.exec(`RCT_NO_LAUNCH_PACKAGER=true \
          cd ios && xcodebuild \
            -scheme ${scheme} build \
            -project playground.xcodeproj \
            -sdk iphonesimulator \
            -derivedDataPath ./DerivedData/playground \
            ${args}`);
}

function e2e() {
  try {
    kill(`detox-server`);
    exec.execAsync(`./node_modules/.bin/detox-server > ./detox-server.log 2>&1`);
    const detoxAppBuildPath = `ios/DerivedData/playground/Build/Products/${release ? 'Release' : 'Debug'}_Detox-iphonesimulator/playground.app`;
    exec.exec(`detoxAppBuildPath="${detoxAppBuildPath}" BABEL_ENV=test ./node_modules/mocha/bin/mocha e2e --recursive --compilers js:babel-register`);
  } finally {
    kill(`detox-server`);
    if (release) {
      kill(`Simulator`);
      kill(`CoreSimulator`);
    }
    exec.exec(`cat ./detox-server.log`);
    exec.exec(`rm -f ./detox-server.log`);
    exec.exec(`sleep 5`);
  }
}

function kill(process) {
  exec.execSilent(`pkill -f "${process}"`);
}

function run() {
  hackReactXcodeScript();
  buildProjForDetox();
  e2e();
}

run();
