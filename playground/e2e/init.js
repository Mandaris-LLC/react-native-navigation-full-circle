/*eslint-disable*/
require('babel-polyfill');
const detox = require('detox');
const detoxConfig = require('../package.json').detox;

detoxConfig['ios-simulator'].app = process.env.detoxAppBuildPath;

before(function(done) {
  this.timeout(30000);
  detox.config(detoxConfig);
  detox.start(done);
});

afterEach(function(done) {
  detox.waitForTestResult(done);
});

after(function(done) {
  detox.cleanup(done);
});
