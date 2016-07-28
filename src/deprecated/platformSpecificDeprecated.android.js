import React, {Component} from 'react';
import {AppRegistry, NativeModules} from 'react-native';
import _ from 'lodash';
import utils from './utils';

import Navigation from './../Navigation';

const resolveAssetSource = require('react-native/Libraries/Image/resolveAssetSource');

import * as newPlatformSpecific from './../platformSpecific';

function startSingleScreenApp(params) {
  const screen = params.screen;
  if (!screen.screen) {
    console.error('startSingleScreenApp(params): screen must include a screen property');
    return;
  }
  addNavigatorParams(screen);
  addNavigatorButtons(screen);
  addNavigationStyleParams(screen);
  screen.passProps = params.passProps;
  //const drawer = setupDrawer(params.drawer);

  /*
   * adapt to new API
   */
  screen.screenId = screen.screen;
  params.screen = adaptNavigationStyleToScreenStyle(screen);
  params.screen = adaptNavigationParams(screen);

  newPlatformSpecific.startApp(params);
}

function navigatorPush(navigator, params) {
  addNavigatorParams(params, navigator);
  addNavigatorButtons(params);
  addNavigationStyleParams(params);

  params.screenId = params.screen;
  let adapted = adaptNavigationStyleToScreenStyle(params);
  adapted = adaptNavigationParams(adapted);

  newPlatformSpecific.push(adapted);
}

function navigatorPop(navigator, params) {
  addNavigatorParams(params, navigator);

  params.screenId = params.screen;
  let adapted = adaptNavigationStyleToScreenStyle(params);
  adapted = adaptNavigationParams(adapted);

  newPlatformSpecific.pop(adapted);
}

function navigatorPopToRoot(navigator, params) {
  addNavigatorParams(params, navigator);

  params.screenId = params.screen;
  let adapted = adaptNavigationStyleToScreenStyle(params);
  adapted = adaptNavigationParams(adapted);

  newPlatformSpecific.popToRoot(adapted);
}

function navigatorResetTo(navigator, params) {
  addNavigatorParams(params, navigator);
  addNavigatorButtons(params);
  addNavigationStyleParams(params);

  params.screenId = params.screen;
  let adapted = adaptNavigationStyleToScreenStyle(params);
  adapted = adaptNavigationParams(adapted);

  newPlatformSpecific.newStack(adapted);
}

function adaptNavigationStyleToScreenStyle(screen) {
  const navigatorStyle = screen.navigatorStyle;
  if (!navigatorStyle) {
    return screen;
  }

  screen.styleParams = {
    statusBarColor: navigatorStyle.statusBarColor,
    topBarColor: navigatorStyle.toolBarColor,
    navigationBarColor: navigatorStyle.navigationBarColor,
    titleBarHidden: navigatorStyle.navBarHidden,
    backButtonHidden: navigatorStyle.backButtonHidden,
    topTabsHidden: navigatorStyle.topTabsHidden,
    bottomTabsHidden: navigatorStyle.tabBarHidden,
    bottomTabsHiddenOnScroll: navigatorStyle.bottomTabsHiddenOnScroll,
    drawUnderTopBar: navigatorStyle.drawUnderNavBar
  };

  return _.omit(screen, ['navigatorStyle']);
}

function adaptNavigationParams(screen) {
  screen.navigationParams = {
    screenInstanceID: screen.screenInstanceID,
    navigatorID: screen.navigatorID,
    navigatorEventID: screen.navigatorEventID
  };
  return _.omit(screen, ['screenInstanceID', 'navigatorID', 'navigatorEventID']);
}

function startTabBasedApp(params) {
  if (!params.tabs) {
   console.error('startTabBasedApp(params): params.tabs is required');
   return;
  }

  params.tabs.forEach(function(tab, idx) {
    debugger;
    addNavigatorParams(tab, null, idx);
    addNavigatorButtons(tab);
    addNavigationStyleParams(tab);
    addTabIcon(tab);
    tab.passProps = params.passProps;

    tab.screenId = tab.screen;
    tab.screen = adaptNavigationStyleToScreenStyle(tab);
    tab.screen = adaptNavigationParams(tab);
  });

  // TODO: add drawer params
  newPlatformSpecific.startApp(params);
}

function addTabIcon(tab) {
  if (tab.icon) {
    const icon = resolveAssetSource(tab.icon);
    if (icon) {
      tab.icon = icon.uri;
    }
  }

  if (!tab.icon) {
    throw new Error("No icon defined for tab " + tab.screen);
  }
}

function navigatorSetButtons(navigator, navigatorEventID, params) {
  if (params.rightButtons) {
    params.rightButtons.forEach(function(button) {
      if (button.icon) {
        const icon = resolveAssetSource(button.icon);
        if (icon) {
          button.icon = icon.uri;
        }
      }
    });
  }
  if (params.leftButton) {
    if (params.leftButton.icon) {
      const icon = resolveAssetSource(params.leftButton.icon);
      if (icon) {
        params.leftButton.icon = icon.uri;
      }
    }
  }
  newPlatformSpecific.setScreenTitleBarButtons(navigator.screenInstanceID, navigatorEventID, params.rightButtons, params.leftButton);
}

function navigatorSetTabBadge(navigator, params) {
  //RctActivity.setTabBadge({
  //  tabIndex: params.tabIndex,
  //  badge: params.badge
  //});
}

function navigatorSetTitle(navigator, params) {
  newPlatformSpecific.setScreenTitleBarTitle(navigator.screenInstanceID, params);
}

function navigatorSwitchToTab(navigator, params) {
  //RctActivity.switchToTab({
  //  navigatorID: navigator.navigatorID,
  //  tabIndex: params.tabIndex
  //});
}

function navigatorToggleDrawer(navigator, params) {
  //RctActivity.toggleDrawer({
  //  side: params.side,
  //  animated: !(params.animated === false),
  //  to: params.to || ''
  //});
}

function navigatorToggleNavBar(navigator, params) {
  const screenInstanceID = navigator.screenInstanceID;
  const visible = params.to === 'shown';
  const animated = !(params.animated === false);

  newPlatformSpecific.toggleTopBarVisible(
    screenInstanceID,
    visible,
    animated
  );
}

function navigatorToggleTabs(navigator, params) {
  //RctActivity.toggleNavigatorTabs({
  //  hidden: params.to === 'hidden',
  //  animated: !(params.animated === false)
  //});
}

function showModal(params) {
  addNavigatorParams(params);
  addNavigatorButtons(params);
  addNavigationStyleParams(params);

  /*
   * adapt to new API
   */
  params.screenId = params.screen;
  let adapted = adaptNavigationStyleToScreenStyle(params);
  adapted = adaptNavigationParams(adapted);

  newPlatformSpecific.showModal(adapted);
}

function dismissModal() {
  newPlatformSpecific.dismissTopModal();
}

function dismissAllModals(params) {
  newPlatformSpecific.dismissAllModals();
}

function addNavigatorParams(screen, navigator = null, idx = '') {
  screen.navigatorID = navigator ? navigator.navigatorID : utils.getRandomId() + '_nav' + idx;
  screen.screenInstanceID = utils.getRandomId();
  screen.navigatorEventID = screen.screenInstanceID + '_events';
}

function addNavigatorButtons(screen) {
  const Screen = Navigation.getRegisteredScreen(screen.screen);
  Object.assign(screen, Screen.navigatorButtons);

  // Get image uri from image id
  const rightButtons = getRightButtons(screen);
  if (rightButtons) {
    rightButtons.forEach(function(button) {
      if (button.icon) {
        const icon = resolveAssetSource(button.icon);
        if (icon) {
          button.icon = icon.uri;
        }
      }
    });
  }

  const leftButton = getLeftButton(screen);
  if (leftButton) {
    if (leftButton.icon) {
      const icon = resolveAssetSource(leftButton.icon);
      if (icon) {
        leftButton.icon = icon.uri;
      }
    }
  }

  if (rightButtons) {
    screen.rightButtons = rightButtons;
  }
  if (leftButton) {
    screen.leftButton = leftButton;
  }
}

function getLeftButton(screen) {
  if (screen.navigatorButtons) {
    return screen.navigatorButtons.leftButton;
  }

  return screen.leftButton
}

function getRightButtons(screen) {
  if (screen.navigatorButtons) {
    return screen.navigatorButtons.rightButtons;
  }

  return screen.rightButtons
}

function addNavigationStyleParams(screen) {
  const Screen = Navigation.getRegisteredScreen(screen.screen);
  screen.navigatorStyle = Object.assign({}, screen.navigatorStyle, Screen.navigatorStyle);
}

function setupDrawer(drawerParams) {
  const drawer = Object.assign({}, drawerParams);
  [drawer.left, drawer.right].forEach(side => {
    if (!side) {
      return;
    }
    const icon = resolveAssetSource(side.icon);
    if (icon) {
      side.icon = icon.uri;
    }
  });
  if (drawer.disableOpenGesture === undefined) {
    drawer.disableOpenGesture = false;
  }

  return drawer;
}

function showFAB(params) {
  //params.icon = resolveAssetSource(params.icon).uri;
  //RctActivity.showFAB(params);
}

export default {
  startTabBasedApp,
  startSingleScreenApp,
  navigatorPush,
  navigatorPop,
  navigatorPopToRoot,
  navigatorResetTo,
  showModal,
  dismissModal,
  dismissAllModals,
  showFAB,
  navigatorSetButtons,
  navigatorSetTabBadge,
  navigatorSetTitle,
  navigatorSwitchToTab,
  navigatorToggleDrawer,
  navigatorToggleTabs,
  navigatorToggleNavBar
};
