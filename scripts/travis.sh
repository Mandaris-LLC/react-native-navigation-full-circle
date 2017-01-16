#!/bin/bash -e

color='\033[1;36m'
nocolor='\033[0m'

run_f () {
  cmd="${1}"
  name=${cmd//[ ]/_}

  echo "travis_fold:start:$name"
  echo -e "${color}\t $cmd \t${nocolor}"

  ($cmd)

  echo "travis_fold:end:$name"
}

run_f "yarn install"
run_f "yarn run lint"
run_f "yarn run test-js"
run_f "yarn run test-android"
run_f "yarn run test-ios"
run_f "yarn run release"
