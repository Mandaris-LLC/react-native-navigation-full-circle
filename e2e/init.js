/*eslint-disable*/
require('babel-polyfill');
const detox = require('detox');

before(async () => {
  const detoxConfig = {
    configurations: {
      ios: {
        binaryPath: process.env.detoxAppBuildPath,
        type: 'simulator',
        name: 'iPhone 7'
      }
    }
  }
  await detox.init(detoxConfig);
});

after(async () => {
  await detox.cleanup();
});
