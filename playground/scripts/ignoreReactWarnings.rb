#!/usr/bin/env ruby

require 'pathname'

$LOAD_PATH.unshift(__dir__ + "/../node_modules/detox/scripts/Xcodeproj/lib")
$LOAD_PATH.unshift(__dir__ + "/../node_modules/detox/scripts/Nanaimo/lib")

require 'xcodeproj'

project = Xcodeproj::Project.open(__dir__ + "/../node_modules/react-native/React/React.xcodeproj")

project.build_configuration_list['Release'].build_settings['WARNING_CFLAGS'] = ['-Wno-shorten-64-to-32','-Wno-unused-parameter','-Wno-unreachable-code','-Wno-deprecated-declarations','-Wno-extra-tokens','-Wno-unused-variable','-Wno-incompatible-pointer-types','-Wno-conditional-uninitialized','-Wno-undeclared-selector','-Wno-objc-protocol-property-synthesis']

raise "Error: Unable to save Xcode project" unless project.save()
