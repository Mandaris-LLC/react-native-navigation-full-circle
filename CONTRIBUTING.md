# Folder Structure
* `lib`: The project itself composed of:</br>
`lib/android`: android sources and unit tests</br>
`lib/ios`: iOS sources and unit tests</br>
`lib/src`: javascript sources and unit tests</br>
`lib/src/index.js`: the entry point for `import Navigation from 'react-native-navigation`

* `e2e`: [detox](https://github.com/wix/detox) iOS e2e tests (in the future, once detox supports it, we will have android e2e here as well)

* `AndroidE2E`: Android e2e tests using native uiautomator

* `playground`: The end-user project all e2e tests run against. Contains its own `src`, `android` and `ios`. Does not have its own package.json, depends on the local `<root>/lib` in order not to go through npm.

* `integration`: misc javascript integration tests
* `scripts`: all scripts


# Running locally

### Environment requirements

* Mac OSX
* Latest stable XCode
* Latest stable Node:

```
curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.1/install.sh | bash
export NVM_DIR="$HOME/.nvm" && [ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh"
nvm install stable
nvm use stable
```

* Latest stable Yarn:

```
curl -o- -L https://yarnpkg.com/install.sh | bash
```

* Android SDK
* fbsimctl:

```
brew tap facebook/fb && brew install fbsimctl
```

### Scripts

`yarn install`: installs dependencies

`yarn run clean`: cleans all build directories, stops packager, fixes flakiness by removing watchman cache, etc.

`yarn run start`: starts the react-native packager for local debugging

`yarn run xcode`: for convenience, opens xcode in this project

`yarn run install-android [-- release]`: builds playground debug/release version and installs on running android devices/emulators

`yarn run uninstall-android`: uninstalls playground from running android devices/simulators

`yarn run test-js`: runs javascript tests and coverage report

`yarn run test-watch`: runs javascript tests in watch mode (can also use the provided wallaby config)

`yarn run test-unit-ios [-- release]`: runs ios unit tests in debug/release

`yarn run test-unit-android [-- release]`: runs android unit tests in debug/release

`yarn run test-e2e-ios [-- release]`: runs the ios e2e suite (with detox) in debug/release

`yarn run test-e2e-android [-- release]`: runs the android e2e suite (with uiautomator) in debug/release on running devices/emulators

`yarn run test-all`: runs all tests