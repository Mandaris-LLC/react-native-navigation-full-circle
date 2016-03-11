import utils from './utils';
import Navigation from './Navigation';
import Controllers from 'react-native-controllers';
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
  const appID = utils.getRandomId();
  const App = Controllers.createClass({
    render: function() {
      return (
        <TabBarControllerIOS id={appID + '_tabs'}>
        {
          params.tabs.map(function(tab, index) {
            const navigatorID = appID + '_nav' + index;
            if (!tab.screen) {
              console.error('startTabBasedApp(params): every tab must include a screen property, take a look at tab#' + (index+1));
              return;
            }
            const screenClass = Navigation.getRegisteredScreen(tab.screen);
            if (!screenClass) {
              console.error('Cannot create screen ' + tab.screen + '. Are you it was registered with Navigation.registerScreen?');
              return;
            }
            const navigatorStyle = Object.assign({}, screenClass.navigatorStyle);
            if (tab.navigatorStyle) {
              Object.assign(navigatorStyle, tab.navigatorStyle);
            }
            return (
              <TabBarControllerIOS.Item {...tab}>
                <NavigationControllerIOS
                  id={navigatorID}
                  title={tab.screenTitle}
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
  ControllerRegistry.registerController(appID, () => App);
  ControllerRegistry.setRootController(appID);
}

function startSingleScreenApp(params) {
  if (!params.screen) {
    console.error('startSingleScreenApp(params): params.screen is required');
    return;
  }
  const appID = utils.getRandomId();
  const App = Controllers.createClass({
    render: function() {
      const screen = params.screen;
      const navigatorID = appID + '_nav';
      if (!screen.screen) {
        console.error('startSingleScreenApp(params): screen must include a screen property');
        return;
      }
      const screenClass = Navigation.getRegisteredScreen(screen.screen);
      if (!screenClass) {
        console.error('Cannot create screen ' + screen.screen + '. Are you it was registered with Navigation.registerScreen?');
        return;
      }
      const navigatorStyle = Object.assign({}, screenClass.navigatorStyle);
      if (screen.navigatorStyle) {
        Object.assign(navigatorStyle, screen.navigatorStyle);
      }
      return (
        <NavigationControllerIOS
          id={navigatorID}
          title={screen.screenTitle}
          component={screen.screen}
          passProps={{navigatorID: navigatorID}}
          style={navigatorStyle}
        />
      );
    }
  });
  ControllerRegistry.registerController(appID, () => App);
  ControllerRegistry.setRootController(appID);
}

function navigatorPush(navigator, params) {
  if (!params.screen) {
    console.error('Navigator.push(params): params.screen is required');
    return;
  }
  const passProps = params.passProps || {};
  const screenClass = Navigation.getRegisteredScreen(params.screen);
  if (!screenClass) {
    console.error('Cannot create screen ' + params.screen + '. Are you it was registered with Navigation.registerScreen?');
    return;
  }
  const navigatorStyle = Object.assign({}, screenClass.navigatorStyle);
  if (params.navigatorStyle) {
    Object.assign(navigatorStyle, params.navigatorStyle);
  }
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

export default platformSpecific = {
  startTabBasedApp,
  startSingleScreenApp,
  navigatorPush,
  navigatorPop
}
