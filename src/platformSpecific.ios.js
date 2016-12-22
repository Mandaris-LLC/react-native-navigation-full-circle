import React, {Component} from 'react';
import {AppRegistry, NativeModules} from 'react-native';
import _ from 'lodash';
import PropRegistry from './PropRegistry';

const NativeAppMAnager = NativeModules.RCCManager;

function startApp(layout) {
	
	NativeAppMAnager.startApp(layout);
}

module.exports = {
	startApp
};
