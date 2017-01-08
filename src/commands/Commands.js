import {NativeModules} from 'react-native';
const {NativeNavigation} = NativeModules;
import * as LayoutBuilder from './LayoutBuilder';

export function startApp(params) {
  NativeNavigation.startApp(LayoutBuilder.parse(params));
}
