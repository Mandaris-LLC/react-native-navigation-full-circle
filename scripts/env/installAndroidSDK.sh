#!/bin/bash -e

scriptdir="$(dirname "${BASH_SOURCE[0]}")"

export ANDROID_HOME=$HOME/android-sdk
export PATH=$PATH:$ANDROID_HOME/tools/bin:$ANDROID_HOME/platform-tools

mkdir $ANDROID_HOME

# fix for https://code.google.com/p/android/issues/detail?id=223424
mkdir -p ~/.android
touch ~/.android/repositories.cfg

echo "Downloading Android SDK"
curl --location https://dl.google.com/android/repository/sdk-tools-darwin-3859397.zip | tar -x -z -C $ANDROID_HOME

echo "Copying Android Licenses"
mkdir -p "${ANDROID_HOME}"/licenses
cp "$scriptdir/android-sdk-licenses/"* "${ANDROID_HOME}"/licenses

package="system-images;android-24;default;armeabi_v7a"
echo "Downloading emulator"
sdkmanager "emulator"
echo "Downloading $package"
sdkmanager "${package}"
echo "Creating avd"
echo no | avdmanager create avd --force --name "pixel" --abi "default/armeabi_v7a" --package "${package}" --device "pixel"
sleep 2
avdmanager list avd
