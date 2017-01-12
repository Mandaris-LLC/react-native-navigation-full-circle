const cp = require('child_process');

function exec(cmd) {
  cp.execSync(cmd, {stdio: ['inherit', 'inherit', 'inherit']});
}

function execSilent(cmd) {
  cp.execSync(cmd, {stdio: ['inherit', 'ignore', 'inherit']});
}

function kill(process) {
  execSilent(`pkill -f "${process}" || true`);
}

function buildProjForDetox() {
  exec(`RCT_NO_LAUNCH_PACKAGER=true \
          cd ios && xcodebuild \
            -scheme playground_Detox build \
            -project playground.xcodeproj \
            -sdk iphonesimulator \
            -derivedDataPath ./DerivedData/playground \
            GCC_PREPROCESSOR_DEFINITIONS="DEBUG=1 RCT_DEBUG=1 RCT_DEV=1 RCT_NSASSERT=1"`);
}

function e2e() {
  try {
    kill(`detox-server`);
    cp.exec(`./node_modules/.bin/detox-server > ./detox-server.log 2>&1`);
    exec(`BABEL_ENV=test ./node_modules/mocha/bin/mocha e2e --recursive --compilers js:babel-register`);
  } finally {
    kill(`detox-server`);
    //kill(`Simulator`);
    //kill(`CoreSimulator`);
    exec(`cat ./detox-server.log`);
    exec(`rm -f ./detox-server.log`);
    exec(`sleep 5`);
  }
}

function run() {
  buildProjForDetox();
  e2e();
}

run();
