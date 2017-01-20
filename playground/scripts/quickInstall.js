const shellUtils = require('shell-utils');

function installNavigation() {
  shellUtils.exec.execSync(`mkdir -p ./node_modules/react-native-navigation`);
  const navigationTar = shellUtils.exec.execSyncRead(`cd .. && npm pack`);
  shellUtils.exec.execSync(`tar -xf ../${navigationTar} -C ./node_modules/react-native-navigation --strip 1`);
  shellUtils.exec.execSync(`rm ../${navigationTar}`);

  if (!process.env.CI) {
    hardlinkNavigation();
  }
}

function copyNodeModulesFromNavigation() {
  shellUtils.exec.execSync(`cp -rf ../node_modules ./`);
}

function removeHardlinks() {
  shellUtils.exec.execSync(`hardlink ./node_modules/react-native-navigation/ -u || true`);
}

function removeNodeModules() {
  shellUtils.exec.execSync(`rm -rf ./node_modules || true`);
}

function ignoreReactWarnings() {
  shellUtils.exec.execSync(`./scripts/ignoreReactWarnings.rb`);
}

function hardlinkNavigation() {
  shellUtils.exec.execSync(`hardlink ../ ./node_modules/react-native-navigation/ || true`);
}

function run() {
  removeHardlinks();
  removeNodeModules();
  copyNodeModulesFromNavigation();
  installNavigation();
  ignoreReactWarnings();
}

run();

