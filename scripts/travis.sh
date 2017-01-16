#!/bin/bash -ex

cd playground
yarn install

echo "************ 1"

RCT_NO_LAUNCH_PACKAGER=true cd ios && xcodebuild -scheme playground_release_Detox build -project playground.xcodeproj -sdk iphonesimulator -derivedDataPath ./DerivedData/playground | xcpretty
cd ..

echo "************ 2"

./node_modules/.bin/detox-server &

echo "************ 3"

detoxAppBuildPath="ios/DerivedData/playground/Build/Products/Release_Detox-iphonesimulator/playground.app" BABEL_ENV=test ./node_modules/mocha/bin/mocha e2e --timeout 240000 --recursive --compilers js:babel-register

echo "************ 4"
