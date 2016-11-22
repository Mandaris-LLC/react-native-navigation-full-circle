/*eslint-disable*/
'use strict';
const babelOptions = JSON.parse(require('fs').readFileSync(`${__dirname}/.babelrc`));
module.exports = function(wallaby) {
  return {
    env: {
      type: 'node',
      runner: 'node'
    },

    testFramework: 'jest',

    files: [
      'package.json',
      'src2/**/*.js',
      '!src2/**/*.test.js'
    ],

    tests: [
      'src2/**/*.test.js'
    ],

    compilers: {
      '**/*.js': wallaby.compilers.babel(babelOptions)
    },

    setup: function(w) {
      require('babel-polyfill');
      w.testFramework.configure(require('./package.json').jest);
    }
  };
};
