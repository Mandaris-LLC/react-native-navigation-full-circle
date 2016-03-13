import utils from './utils';
import Navigation from './Navigation';
import Controllers, { Modal } from 'react-native-controllers';
const React = Controllers.hijackReact();
const {
  ControllerRegistry,
  TabBarControllerIOS,
  NavigationControllerIOS,
  DrawerControllerIOS
} = React;

function startTabBasedApp(params) {
  if (!params.tabs) {
    console.error('startTabBasedApp(params): params.tabs is required');
    return;
  }
  const controllerID = utils.getRandomId();
  const Controller = Controllers.createClass({
    render: function() {
      return (
        <TabBarControllerIOS id={controllerID + '_tabs'}>
        {
          params.tabs.map(function(tab, index) {
            const navigatorID = controllerID + '_nav' + index;
            if (!tab.screen) {
              console.error('startTabBasedApp(params): every tab must include a screen property, take a look at tab#' + (index+1));
              return;
            }
            const { navigatorStyle } = _mergeScreenSpecificSettings(tab.screen, tab);
            return (
              <TabBarControllerIOS.Item {...tab} title={tab.label}>
                <NavigationControllerIOS
                  id={navigatorID}
                  title={tab.title}
                  component={tab.screen}
                  passProps={{navigatorID: navigatorID}}
                  style={navigatorStyle}
                />
              </TabBarControllerIOS.Item>
            );
          })
        }
        </TabBarControllerIOS>
      );
    }
  });
  ControllerRegistry.registerController(controllerID, () => Controller);
  ControllerRegistry.setRootController(controllerID);
}

function startSingleScreenApp(params) {
  if (!params.screen) {
    console.error('startSingleScreenApp(params): params.screen is required');
    return;
  }
  const controllerID = utils.getRandomId();
  const Controller = Controllers.createClass({
    render: function() {
      const screen = params.screen;
      const navigatorID = controllerID + '_nav';
      if (!screen.screen) {
        console.error('startSingleScreenApp(params): screen must include a screen property');
        return;
      }
      const { navigatorStyle } = _mergeScreenSpecificSettings(screen.screen, screen);
      return (
        <NavigationControllerIOS
          id={navigatorID}
          title={screen.title}
          component={screen.screen}
          passProps={{navigatorID: navigatorID}}
          style={navigatorStyle}
        />
      );
    }
  });
  ControllerRegistry.registerController(controllerID, () => Controller);
  ControllerRegistry.setRootController(controllerID);
}

function _mergeScreenSpecificSettings(screenID, params) {
  const screenClass = Navigation.getRegisteredScreen(screenID);
  if (!screenClass) {
    console.error('Cannot create screen ' + screenID + '. Are you it was registered with Navigation.registerScreen?');
    return;
  }
  const navigatorStyle = Object.assign({}, screenClass.navigatorStyle);
  if (params.navigatorStyle) {
    Object.assign(navigatorStyle, params.navigatorStyle);
  }
  return { navigatorStyle };
}

function navigatorPush(navigator, params) {
  if (!params.screen) {
    console.error('Navigator.push(params): params.screen is required');
    return;
  }
  const { navigatorStyle } = _mergeScreenSpecificSettings(params.screen, params);
  const passProps = params.passProps || {};
  passProps.navigatorID = navigator.navigatorID;
  Controllers.NavigationControllerIOS(navigator.navigatorID).push({
    title: params.title,
    component: params.screen,
    animated: params.animated,
    passProps: passProps,
    style: navigatorStyle,
    backButtonTitle: params.backButtonTitle
  });
}

function navigatorPop(navigator, params) {
  Controllers.NavigationControllerIOS(navigator.navigatorID).pop({
    animated: params.animated
  });
}

function showModal(params) {
  if (!params.screen) {
    console.error('showModal(params): params.screen is required');
    return;
  }
  const { navigatorStyle } = _mergeScreenSpecificSettings(params.screen, params);
  const controllerID = utils.getRandomId();
  const Controller = Controllers.createClass({
    render: function() {
      const navigatorID = controllerID + '_nav';
      const { navigatorStyle } = _mergeScreenSpecificSettings(params.screen, params);
      return (
        <NavigationControllerIOS
          id={navigatorID}
          title={params.title}
          component={params.screen}
          passProps={{navigatorID: navigatorID}}
          style={navigatorStyle}
        />
      );
    }
  });
  ControllerRegistry.registerController(controllerID, () => Controller);
  Modal.showController(controllerID, params.animationType);
}

function dismissModal(params) {
  Modal.dismissController(params.animationType);
}

export default platformSpecific = {
  startTabBasedApp,
  startSingleScreenApp,
  navigatorPush,
  navigatorPop,
  showModal,
  dismissModal
}
