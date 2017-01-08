import _ from 'lodash';
import {NativeModules} from 'react-native';
const {NativeNavigation} = NativeModules;
import * as LayoutBuilder from './LayoutBuilder';

export function startApp(params) {
  NativeNavigation.startApp(LayoutBuilder.parse(params));
}
//
//function parseParams(params) {
//  const msg = `invalid params passed to startApp: ${params}`;
//  if (!params) {
//    throw new Error(msg);
//  }
//
//  if (params.tabs) {
//    return parseTabs(params);
//  }
//
//  if (params.container) {
//    return parseContainer(params);
//  }
//
//  throw new Error(msg);
//}
//
//function parseTabs(params) {
////&& params.tabs.length > 0 && _.every(params.tabs, (t) => t.containerKey)
//}
//
//function parseContainer(params) {
//
//}

