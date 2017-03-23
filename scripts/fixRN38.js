const _ = require('lodash');
const shellUtils = require('shell-utils');
const fs = require('fs');

function run() {
  console.log('fixing RCTJSStackFrame.m to not break analysis'); //eslint-disable-line

  const path = `${process.cwd()}/node_modules/react-native/React/Base/RCTJSStackFrame.h`;
  const lines = String(fs.readFileSync(path)).split('\n');
  lines[24] = '+ (NSArray<RCTJSStackFrame *> *)stackFramesWithDictionaries:(NSArray *)dicts;';
  fs.writeFileSync(path, lines.join('\n'));
}

run();
