const exec = require('shell-utils').exec;
run();

function run() {
  exec.execSync(`cd lib/android && ./gradlew clean unitTest`);
}
