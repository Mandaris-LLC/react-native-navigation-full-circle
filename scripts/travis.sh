#!/bin/bash -e

run_f () {
  cmd="${1}"

  echo "travis_fold:start:$cmd"
  ($cmd)
  echo "travis_fold:end:$cmd"
}

run_f "yarn install"
run_f "yarn run lint"
run_f "yarn run test-js"
run_f "yarn run test-android"
run_f "yarn run test-ios"
run_f "yarn run release"
