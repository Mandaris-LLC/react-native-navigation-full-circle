/*eslint-disable*/
require('babel-polyfill');
const detox = require('detox');
const config = require('../package.json').detox;

before(function(done) {
  this.timeout(60000);
  detox.config(config);
  detox.start(done);
});

afterEach(function(done) {
  detox.waitForTestResult(done);
});

after(function(done) {
  detox.cleanup(done);
});
