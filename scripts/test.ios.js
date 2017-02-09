const exec = require('shell-utils').exec;

function run() {
  process.chdir('./playground');
  exec.execSync(`yarn run quickinstall`);
  exec.execSync(`yarn run xcodeunit`);
  exec.execSync(`yarn run e2e-ios release`);
  process.chdir('../');
}

run();
