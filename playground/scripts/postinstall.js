const shellUtils = require('shell-utils');

shellUtils.exec.execSync(`rm yarn.lock || true`);
shellUtils.exec.execSync(`./scripts/ignoreReactWarnings.rb`);
shellUtils.exec.execSync(`rm -rf node_modules/react-native-navigation/node_modules`);
shellUtils.exec.execSync(`rm -rf node_modules/react-native-navigation/playground`);
