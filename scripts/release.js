/*eslint-disable no-console*/
const shellUtils = require('shell-utils');
const p = require('path');
const semver = require('semver');

function validateEnv() {
  if (!process.env.CI || !process.env.TRAVIS) {
    throw new Error(`releasing is only available from Travis CI`);
  }

  if (process.env.TRAVIS_BRANCH !== 'master') {
    console.log(`not publishing on branch ${process.env.TRAVIS_BRANCH}`);
    return false;
  }

  if (process.env.TRAVIS_PULL_REQUEST !== 'false') {
    console.log(`not publishing as triggered by pull request ${process.env.TRAVIS_PULL_REQUEST}`);
    return false;
  }

  return true;
}

function setupGit() {
  shellUtils.exec.execSyncSilent(`git config --global push.default simple`);
  shellUtils.exec.execSyncSilent(`git config --global user.email "${process.env.GIT_EMAIL}"`);
  shellUtils.exec.execSyncSilent(`git config --global user.name "${process.env.GIT_USER}"`);
  const remoteUrl = new RegExp(`https?://(\\S+)`).exec(shellUtils.exec.execSyncRead(`git remote -v`))[1];
  shellUtils.exec.execSyncSilent(`git remote add deploy "https://${process.env.GIT_USER}:${process.env.GIT_TOKEN}@${remoteUrl}"`);
  shellUtils.exec.execSync(`git checkout master`);
}

function calcNewVersion() {
  const nextTaggedVersion = shellUtils.exec.execSyncRead(`npm view ${process.env.npm_package_name}@next version`);
  console.log(`next tagged version is: ${nextTaggedVersion}`);
  return semver.inc(nextTaggedVersion, 'prerelease');
}

function copyNpmRc() {
  const npmrcPath = p.resolve(`${__dirname}/.npmrc`);
  shellUtils.exec.execSync(`cp -rf ${npmrcPath} .`);
}

function tagAndPublish(newVersion) {
  console.log(`new version is: ${newVersion}`);
  shellUtils.exec.execSync(`npm version ${newVersion} -m "v${newVersion} [ci skip]"`);
  shellUtils.exec.execSyncSilent(`git push deploy --tags`);
  shellUtils.exec.execSync(`npm publish --tag next`);
}

function run() {
  if (!validateEnv()) {
    return;
  }
  setupGit();
  copyNpmRc();
  tagAndPublish(calcNewVersion());
}

run();
