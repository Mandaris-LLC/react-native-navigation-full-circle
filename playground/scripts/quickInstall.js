const shellUtils = require('shell-utils');

function installNavigation() {
  shellUtils.exec.execSync(`mkdir -p ./node_modules/react-native-navigation`);
  const navigationTar = shellUtils.exec.execSyncRead(`cd .. && npm pack`);
  shellUtils.exec.execSync(`tar -xf ../${navigationTar} -C ./node_modules/react-native-navigation --strip 1`);
  shellUtils.exec.execSync(`rm ../${navigationTar}`);
}

function copyNodeModulesFromNavigation() {
  shellUtils.exec.execSync(`cp -rf ../node_modules ./`);
}

function run() {
  exec.execSyncSilent(`hardlink ./node_modules/react-native-navigation/ -u || true`);
  exec.execSyncSilent(`rm -rf ./node_modules || true`);
  copyNodeModulesFromNavigation();
  installNavigation();
  shellUtils.exec.execSync(`./scripts/ignoreReactWarnings.rb`);
  if (!process.env.CI) {
    exec.execSyncSilent(`hardlink ../ ./node_modules/react-native-navigation/ || true`);
  }
}

run();

