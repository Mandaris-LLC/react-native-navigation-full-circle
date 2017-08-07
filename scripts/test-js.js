const exec = require('shell-utils').exec;
const _ = require('lodash');

const dirs = [
  'lib/src',
  'integration',
  'e2e',
  'scripts',
  'playground/src'
];

run();

function run() {
  const paths = _.chain(dirs).map((d) => `${d}/**/**/**/**/**/**/*`).join(' ').value();
  exec.execSync(`BABEL_ENV=test xo ${paths}`);
  exec.execSync(`BABEL_ENV=test jest --coverage`);
}
