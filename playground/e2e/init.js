/*eslint-disable*/
require('babel-polyfill');
const detox = require('detox');
const detoxConfig = require('../package.json').detox;

const release = process.env.detoxMode === 'release';

before(function(done) {
  this.timeout(30000);
  detoxConfig['ios-simulator'].app = `ios/DerivedData/playground/Build/Products/${release ? 'Release' : 'Debug'}_Detox-iphonesimulator/playground.app`;
  detox.config(detoxConfig);
  detox.start(done);
});

afterEach(function(done) {
  detox.waitForTestResult(done);
});

after(function(done) {
  detox.cleanup(done);
});
