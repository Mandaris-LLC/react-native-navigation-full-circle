const exec = require('shell-utils').exec;

function run() {
  process.chdir('./playground');
  process.chdir('../');
}

run();
