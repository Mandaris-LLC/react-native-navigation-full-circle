const shellUtils = require('shell-utils');

function installNavigation() {
  shellUtils.exec.execSync(`mkdir -p ./node_modules/react-native-navigation`);
  const navigationTar = shellUtils.exec.execSyncRead(`cd .. && npm pack`);
  shellUtils.exec.execSync(`tar -xf ../${navigationTar} -C ./node_modules/react-native-navigation --strip 1`);
  shellUtils.exec.execSync(`rm ../${navigationTar}`);
}

function run() {
  shellUtils.exec.execSync(`rm yarn.lock || true`);
  shellUtils.exec.execSync(`./scripts/ignoreReactWarnings.rb`);

  installNavigation();
}

run();

