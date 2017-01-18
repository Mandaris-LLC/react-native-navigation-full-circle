const shellUtils = require('shell-utils');

shellUtils.exec.execSync(`rm yarn.lock || true`);
shellUtils.exec.execSync(`./scripts/ignoreReactWarnings.rb`);

shellUtils.exec.execSync(`mkdir -p ./node_modules/react-native-navigation`);
shellUtils.exec.execSync(`cd .. && npm pack`);
//shellUtils.exec.execSync(`tar -xf ../react-native-navigation.tar`);
