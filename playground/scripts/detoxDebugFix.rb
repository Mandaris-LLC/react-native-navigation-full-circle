#!/usr/bin/env ruby

require 'pathname'

$LOAD_PATH.unshift(__dir__ + "/../node_modules/detox/scripts/Xcodeproj/lib")
$LOAD_PATH.unshift(__dir__ + "/../node_modules/detox/scripts/Nanaimo/lib")

require 'xcodeproj'

isAlwaysDebug = ARGV.count > 0 && ARGV[0].to_s.eql?('debug')

project = Xcodeproj::Project.open(__dir__ + "/../node_modules/react-native/React/React.xcodeproj")

debug_preprocessor_macros = project.build_configuration_list['Debug'].build_settings['GCC_PREPROCESSOR_DEFINITIONS']
release_preprocessor_macros = project.build_configuration_list['Release'].build_settings['GCC_PREPROCESSOR_DEFINITIONS']

unless debug_preprocessor_macros.kind_of?(Array)
  debug_preprocessor_macros = [debug_preprocessor_macros]
end
unless release_preprocessor_macros.kind_of?(Array)
  release_preprocessor_macros = [release_preprocessor_macros]
end

if isAlwaysDebug then
  release_preprocessor_macros = release_preprocessor_macros | debug_preprocessor_macros
else
  release_preprocessor_macros = release_preprocessor_macros - debug_preprocessor_macros
end

project.build_configuration_list['Release'].build_settings['GCC_PREPROCESSOR_DEFINITIONS'] = release_preprocessor_macros

project.build_configuration_list['Release'].build_settings['WARNING_CFLAGS'] = ['-Wno-shorten-64-to-32','-Wno-unused-parameter','-Wno-unreachable-code','-Wno-deprecated-declarations','-Wno-extra-tokens','-Wno-unused-variable','-Wno-incompatible-pointer-types','-Wno-conditional-uninitialized','-Wno-undeclared-selector','-Wno-objc-protocol-property-synthesis']

raise "Error: Unable to save Xcode project" unless project.save()
