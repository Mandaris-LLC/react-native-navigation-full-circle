const exec = require('shell-utils').exec;

function run() {
  exec.execSync(`cd playground && hardlink node_modules/react-native-navigation/ -u || true`);
  exec.execSync(`cd playground && yarn install`);
  //exec.execSync(`cd playground && yarn run e2e release`);
}

run();
