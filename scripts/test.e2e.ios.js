const _ = require('lodash');
const exec = require('shell-utils').exec;

const release = _.includes(process.argv, '--release');

run();

function run() {
  try {
    startRecording();
    const conf = release ? `release` : `debug`;
    exec.execSync(`detox build --configuration ios.sim.${conf} && detox test --configuration ios.sim.${conf} ${process.env.CI ? '--cleanup' : ''}`);
  } finally {
    stopRecording();
  }
}

function startRecording() {
  exec.execSync(`ffmpeg -f avfoundation -list_devices true -i ""`);
  const screenId = exec.execSyncRead(`ffmpeg -f avfoundation -list_devices true -i "" 2>&1 | grep "Capture screen 0" | sed -e "s/.*\\[//" -e "s/\\].*//"`);
  exec.execAsync(`ffmpeg -f avfoundation -i "${screenId}:none" out.avi`);
}

function stopRecording() {
  exec.execSync(`killall ffmpeg`);
  exec.execSync(`npm run release`);
}
