const cp = require('child_process');

function exec(cmd) {
  cp.execSync(cmd, {stdio: ['inherit', 'inherit', 'inherit']});
}

function execSilent(cmd) {
  cp.execSync(`${cmd} || true`, {stdio: ['ignore', 'ignore', 'ignore']});
}

function execRead(cmd) {
  return String(cp.execSync(cmd, {stdio: ['pipe', 'pipe', 'pipe']})).trim();
}

function execAsync(cmd) {
  cp.exec(cmd);
}

module.exports = {
  exec,
  execSilent,
  execRead,
  execAsync
};

