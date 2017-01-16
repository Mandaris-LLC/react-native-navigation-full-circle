#!/bin/bash -e

cd playground
detoxAppBuildPath="ios/DerivedData/playground/Build/Products/Release_Detox-iphonesimulator/playground.app" BABEL_ENV=test ./node_modules/mocha/bin/mocha e2e --timeout 120000 --recursive --compilers js:babel-register
cd ..
