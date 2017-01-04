import * as ContainerRegistry from './containers/ContainerRegistry';

export function registerContainer(containerKey, getContainerFunc) {
  ContainerRegistry.registerContainer(containerKey, getContainerFunc);
}

export function startApp(params) {
  //
}

export function push(params) {
  //
}

export function pop(params) {
  //
}

export function showModal(params) {
  //
}

export function dismissModal(params) {
  //
}

export function dismissAllModals(params) {
  //
}

export function showLightbox(params) {
  //
}

export function dismissLightbox(params) {
  //
}

export function showInAppNotification(params) {
  //
}

export function dismissInAppNotification(params) {
  //
}

export function popToRoot(params) {
  //
}

export function newStack(params) {
  //
}

// new work on src/Navigation:
//+function startApp(layout) {
//  +    return platformSpecific.startApp(layout);
//  +}
//+
//+  startSingleScreenApp: startSingleScreenApp,
//  +  startApp: startApp

// new work on src/newParams.json:
//
//{
//  "screen": {
//  "screen": "example.FirstTabScreen",
//    "title": "Navigation",
//    "navigatorStyle": {
//    "navBarBackgroundColor": "#4dbce9",
//      "navBarTextColor": "#ffff00",
//      "navBarSubtitleTextColor": "#ff0000",
//      "navBarButtonColor": "#ffffff",
//      "statusBarTextColorScheme": "light",
//      "tabBarBackgroundColor": "#4dbce9",
//      "tabBarButtonColor": "#ffffff",
//      "tabBarSelectedButtonColor": "#ffff00"
//  },
//  "navigatorID": "navigatorID1_nav",
//    "screenInstanceID": "screenInstanceID2",
//    "navigatorEventID": "screenInstanceID2_events",
//    "navigatorButtons": {
//    "leftButtons": [
//      {
//        "icon": "http://localhost:8081/assets/img/navicon_menu@2x.png?platform=ios&hash=940519e495eac2a6236034f2bf88ce90",
//        "id": "menu"
//      }
//    ],
//      "rightButtons": [
//      {
//        "title": "Edit",
//        "id": "edit",
//        "enabled": true
//      },
//      {
//        "icon": "http://localhost:8081/assets/img/navicon_add@2x.png?platform=ios&hash=667fefa47fb5daebbc3943669dc6eb26",
//        "id": "add",
//        "enabled": true
//      }
//    ]
//  },
//  "screenId": "example.FirstTabScreen",
//    "styleParams": {
//    "topBarColor": 4283284713,
//      "titleBarTitleColor": 4294967040,
//      "titleBarButtonColor": 4294967295,
//      "drawBelowTopBar": true,
//      "drawScreenAboveBottomTabs": true,
//      "bottomTabsColor": 4283284713,
//      "bottomTabsButtonColor": 4294967295,
//      "bottomTabsSelectedButtonColor": 4294967040
//  },
//  "navigationParams": {
//    "screenInstanceID": "screenInstanceID2",
//      "navigatorID": "navigatorID1_nav",
//      "navigatorEventID": "screenInstanceID2_events"
//  }
//},
//  "drawer": {
//  "left": {
//    "screen": "example.SideMenu"
//  }
//},
//  "appStyle": null,
//  "sideMenu": {
//  "left": {
//    "screenId": "example.SideMenu",
//      "navigatorID": "navigatorID3_nav",
//      "screenInstanceID": "screenInstanceID4",
//      "navigatorEventID": "screenInstanceID4_events",
//      "navigationParams": {
//      "screenInstanceID": "screenInstanceID4",
//        "navigatorID": "navigatorID3_nav",
//        "navigatorEventID": "screenInstanceID4_events"
//    }
//  },
//  "right": null
//},
//  "animateShow": true
//}

// new work on platformSpecificDeprecated.ios.js:
import Navigation from './../Navigation';
//import Controllers, {Modal, Notification} from './controllers';
//+import _ from 'lodash';
//+import PropRegistry from '../PropRegistry';
//+import * as newPlatformSpecific from './../platformSpecific';
//+
//const React = Controllers.hijackReact();
//const {
//  ControllerRegistry,
//  TabBarControllerIOS,
//  NavigationControllerIOS,
//  DrawerControllerIOS
//} = React;
//-import _ from 'lodash';
//
//-import PropRegistry from '../PropRegistry';
//+function startApp(layout) {
//  +	newPlatformSpecific.startApp(layout);
//  +}
//
//function startTabBasedApp(params) {
//  if (!params.tabs) {
//  @@ -566,6 +571,7 @@ function dismissContextualMenu() {
//    }
//
//    export default {
//    +  startApp,
//    startTabBasedApp,
//      startSingleScreenApp,
//      navigatorPush,

//new work on platformSpecific.ios.js:
//-module.exports = {};
//+import React, {Component} from 'react';
//+import {AppRegistry, NativeModules} from 'react-native';
//+import _ from 'lodash';
//+import PropRegistry from './PropRegistry';
//+
//  +const NativeAppMAnager = NativeModules.RCCManager;
//+
//  +function startApp(layout) {
//    +
//      +	NativeAppMAnager.startApp(layout);
//    +}
//+
//+module.exports = {
//                  +	startApp
//                  +};
