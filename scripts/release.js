/*eslint-disable no-console*/
const exec = require('shell-utils').exec;
const p = require('path');
const semver = require('semver');

const VERSION_TAG = 'alpha';
const VERSION_INC = 'prerelease';

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
  exec.execSyncSilent(`git config --global push.default simple`);
  exec.execSyncSilent(`git config --global user.email "${process.env.GIT_EMAIL}"`);
  exec.execSyncSilent(`git config --global user.name "${process.env.GIT_USER}"`);
  const remoteUrl = new RegExp(`https?://(\\S+)`).exec(exec.execSyncRead(`git remote -v`))[1];
  exec.execSyncSilent(`git remote add deploy "https://${process.env.GIT_USER}:${process.env.GIT_TOKEN}@${remoteUrl}"`);
  exec.execSync(`git checkout master`);
}

function calcNewVersion() {
  const currentVersion = exec.execSyncRead(`npm view ${process.env.npm_package_name}@${VERSION_TAG} version`);
  console.log(`${VERSION_TAG} version: ${currentVersion}`);
  const packageVersion = process.env.npm_package_version;
  console.log(`package version: ${packageVersion}`);
  const greater = semver.gt(currentVersion, packageVersion) ? currentVersion : packageVersion;
  return semver.inc(greater, VERSION_INC);
}

function copyNpmRc() {
  const npmrcPath = p.resolve(`${__dirname}/.npmrc`);
  exec.execSync(`cp -Rf ${npmrcPath} .`);
}

function tagAndPublish(newVersion) {
  console.log(`new version is: ${newVersion}`);
  exec.execSync(`npm version ${newVersion} -m "v${newVersion} [ci skip]"`);
  exec.execSyncSilent(`git push deploy --tags`);
  exec.execSync(`npm publish --tag ${VERSION_TAG}`);
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
