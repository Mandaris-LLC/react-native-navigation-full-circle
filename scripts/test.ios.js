const exec = require('shell-utils').exec;

function run() {
  process.chdir('./playground');
  exec.execSync(`hardlink node_modules/react-native-navigation/ -u || true`);
  exec.execSync(`yarn install`);
  exec.execSync(`yarn run e2e release`);
  process.chdir('../');
}

run();
