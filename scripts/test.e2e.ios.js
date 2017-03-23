const _ = require('lodash');
const exec = require('shell-utils').exec;

const release = _.includes(process.argv, 'release');

function buildProjForDetox() {
  const scheme = release ? `playground_release` : `playground`;

  const cmd = `cd ./playground/ios && xcodebuild
            -scheme ${scheme}
            ${release ? 'clean build' : 'build'}
            -project playground.xcodeproj
            -sdk iphonesimulator
            -derivedDataPath ./DerivedData/playground`;

  if (exec.which(`xcpretty`)) {
    exec.execSync(`${cmd} | xcpretty && exit \${PIPESTATUS[0]}`);
  } else {
    exec.execSync(`${cmd}`);
  }
}

function e2e() { //eslint-disable-line
  try {
    exec.execSyncSilent(`watchman watch-del-all || true`);
    const detoxAppBuildPath = `playground/ios/DerivedData/playground/Build/Products/${release ? 'Release' : 'Debug'}-iphonesimulator/playground.app`;

    exec.execSync(`detoxAppBuildPath="${detoxAppBuildPath}"
                              BABEL_ENV=test
                              ./node_modules/mocha/bin/mocha e2e
                                --timeout ${2 * 60 * 1000}
                                --recursive
                                --bail`);
  } finally {
    if (process.env.CI) {
      exec.kill(`Simulator`);
      exec.kill(`CoreSimulator`);
      exec.execSync(`sleep 5`);
    }
  }
}

function run() {
  if (!exec.which(`fbsimctl`)) {
    throw new Error(`fbsimctl must be installed: "brew tap facebook/fb && brew install fbsimctl"`);
  }
  buildProjForDetox();
  e2e();
}

run();
