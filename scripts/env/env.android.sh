#!/bin/bash -e

scriptdir="$(dirname "${BASH_SOURCE[0]}")"

export ANDROID_HOME=$HOME/android-sdk
export PATH=$PATH:$ANDROID_HOME/tools/bin:$ANDROID_HOME/platform-tools

mkdir $ANDROID_HOME

# fix for https://code.google.com/p/android/issues/detail?id=223424
mkdir -p ~/.android
touch ~/.android/repositories.cfg

echo "Downloading Android SDK"
brew --version
brew config
brew tap caskroom/cask
brew cask install android-sdk

echo "Accepting Android Licenses"
yes | sdkmanager --licenses
