const _ = require('lodash');
const shellUtils = require('shell-utils');
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

  shellUtils.exec.execSync(`./scripts/detoxDebugFix.rb ${release ? '' : 'debug'}`);

  shellUtils.exec.execSync(`RCT_NO_LAUNCH_PACKAGER=true
          cd ios && xcodebuild
            -scheme ${scheme} build
            -project playground.xcodeproj
            -sdk iphonesimulator
            -derivedDataPath ./DerivedData/playground`);
}

function e2e() {
  try {
    shellUtils.exec.execSyncSilent(`watchman watch-del-all || true`);
    shellUtils.exec.kill(`detox-server`);
    shellUtils.exec.exec(`./node_modules/.bin/detox-server > ./detox-server.log 2>&1`);
    const detoxAppBuildPath = `ios/DerivedData/playground/Build/Products/${release ? 'Release' : 'Debug'}_Detox-iphonesimulator/playground.app`;

    shellUtils.exec.execSync(`detoxAppBuildPath="${detoxAppBuildPath}" 
                              BABEL_ENV=test
                              ./node_modules/mocha/bin/mocha e2e 
                                --timeout 120000 
                                --recursive
                                --compilers js:babel-register`);
  } finally {
    shellUtils.exec.execSync(`./scripts/detoxDebugFix.rb`);
    shellUtils.exec.kill(`detox-server`);
    if (release) {
      shellUtils.exec.kill(`Simulator`);
      shellUtils.exec.kill(`CoreSimulator`);
    }
    shellUtils.exec.execSync(`cat ./detox-server.log`);
    shellUtils.exec.execSync(`rm -f ./detox-server.log`);
    shellUtils.exec.execSync(`sleep 5`);
  }
}

function run() {
  hackReactXcodeScript();
  buildProjForDetox();
  e2e();
}

run();
