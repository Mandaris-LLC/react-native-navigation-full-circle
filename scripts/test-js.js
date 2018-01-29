const exec = require('shell-utils').exec;
const _ = require('lodash');

const fix = _.includes(process.argv, '--fix') ? '--fix' : '';

const dirs = [
  'lib/src',
  'integration',
  'e2e',
  'scripts',
  'playground/src'
];

run();

function run() {
  const paths = _.chain(dirs).map((d) => `'${d}/**/*.[tj]s*'`).join(' ').value();
  exec.execSync(`tslint ${paths} ${fix} --format verbose`);
  exec.execSync(`jest --coverage`);
}
