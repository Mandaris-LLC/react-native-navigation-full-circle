# React Native Navigation

App-wide support for 100% native navigation with potential isolation support. For iOS, this package is a wrapper around [react-native-controllers](https://github.com/wix/react-native-controllers) which provides a simplified more abstract API. This abstract API will be unified with the Android solution which is still work in progress.

## Installation - iOS

 * In your project folder run `npm install react-native-navigation --save`
 
 * Add the native files of the dependency [react-native-controllers](https://github.com/wix/react-native-controllers) to your Xcode project:

   * In Xcode, in Project Navigator (left pane), right-click on the `Libraries` > `Add files to [project name]`. Add `./node_modules/react-native-controllers/ios/ReactNativeControllers.xcodeproj` ([screenshots](https://facebook.github.io/react-native/docs/linking-libraries-ios.html#step-1))
 
   * In Xcode, in Project Navigator (left pane), click on your project (top) and select the `Build Phases` tab (right pane). In the `Link Binary With Libraries` section add `libReactNativeControllers.a` ([screenshots](https://facebook.github.io/react-native/docs/linking-libraries-ios.html#step-2))

   * In Xcode, in Project Navigator (left pane), click on your project (top) and select the `Build Settings` tab (right pane). In the `Header Search Paths` section add `$(SRCROOT)/../node_modules/react-native-controllers/ios`. Make sure on the right to mark this new path `recursive` ([screenshots](https://facebook.github.io/react-native/docs/linking-libraries-ios.html#step-3))

 * In Xcode, under your project files, modify `AppDelegate.m` according to this [example](https://github.com/wix/react-native-navigation/blob/master/example/ios/example/AppDelegate.m)
 
 * Make sure you are using react-native version >= 0.19.0
 
## Installation - Android

Coming soon, not yet supported

## Usage

If you don't like reading, just jump into the fully working [example project](https://github.com/wix/react-native-navigation/tree/master/example).

#### Step 1 - Change the way your app starts

This would normally go in your `index.ios.js`

```js
import { Navigation } from 'react-native-navigation';

// import the components for your root screens (or the packager will not bundle them)
// they all need to be registered with Navigation.registerScreen
import './FirstTabScreen';
import './SecondTabScreen';

// start the app
Navigation.startTabBasedApp({
  tabs: [
    {
      label: 'One',
      screen: 'example.FirstTabScreen',
      icon: require('../img/one.png'),
      selectedIcon: require('../img/one_selected.png'),
      title: 'Screen One'
    },
    {
      label: 'Two',
      screen: 'example.SecondTabScreen',
      icon: require('../img/two.png'),
      selectedIcon: require('../img/two_selected.png'),
      title: 'Screen Two'
    }
  ]
});
```

#### Step 2 - Slightly modify your screen components

Every screen that you want to be able to place in a tab, push to the navigation stack or present modally needs to follow two basic conventions:

1. Normally your React components extend `React.Component`, in order to get access to the `navigator` instance you need to extend `Screen` instead.

2. You need to register your component since it's displayed as a separate React root. Register a unique ID with `Navigation.registerScreen`.

> Note: Since your screens will potentially be bundled with other packages, your registered name must be **unique**! Follow a namespacing convention like `packageName.ScreenName`.

```js
import { Navigation, Screen } from 'react-native-navigation';

class ExampleScreen extends Screen {
  static navigatorStyle = {}; // style the navigator for this screen (optional)
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <View style={styles.container}>...</View>
    );
  }
}

// register all screens with Navigation.registerScreen
Navigation.registerScreen('example.ScreenOne', () => ExampleScreen);
```

## Top Level API

#### `Navigation`

```js
import { Navigation } from 'react-native-navigation';
```

 * **registerScreen(screenID, generator)**
 
Every screen used must be registered with a unique name.

```js
Navigation.registerScreen('example.FirstTabScreen', () => FirstTabScreen);
```

 * **startTabBasedApp(params)**
 
Change your app root into an app based on several tabs (usually 2-5), a very common pattern in iOS (like Facebook app or the iOS Contacts app). Every tab has its own navigation stack with a native nav bar.

```js
Navigation.startTabBasedApp({
  tabs: [
    {
      label: 'One', // tab label as appears under the icon in iOS (optional)
      screen: 'example.FirstTabScreen', // unique ID registered with Navigation.registerScreen
      icon: require('../img/one.png'), // local image asset for the tab icon unselected state (optional)
      selectedIcon: require('../img/one_selected.png'), // local image asset for the tab icon selected state (optional)
      title: 'Screen One', // title of the screen as appears in the nav bar (optional)
      navigatorStyle: {} // override the navigator style for the tab screen, see "Styling the navigator" below (optional)
    },
    {
      label: 'Two',
      screen: 'example.SecondTabScreen',
      icon: require('../img/two.png'),
      selectedIcon: require('../img/two_selected.png'),
      title: 'Screen Two'
    }
  ]
});
```

 * **startSingleScreenApp(params)**
 
Change your app root into an app based on a single screen (like the iOS Calendar or Settings app). The screen will receive its own navigation stack with a native nav bar

```js
Navigation.startSingleScreenApp({
  screen: {
    screen: 'example.WelcomeScreen', // unique ID registered with Navigation.registerScreen
    title: 'Welcome', // title of the screen as appears in the nav bar (optional)
    navigatorStyle: {} // override the navigator style for the screen, see "Styling the navigator" below (optional)
  }
});
```

 * **showModal(params = {})**

Show a screen as a modal.
 
```js
Navigation.showModal({
  screen: "example.ModalScreen", // unique ID registered with Navigation.registerScreen
  title: "Modal", // title of the screen as appears in the nav bar (optional)
  passProps: {}, // simple serializable object that will pass as props to the modal (optional)
  navigatorStyle: {}, // override the navigator style for the screen, see "Styling the navigator" below (optional)
  animationType: 'slide-up' // 'none' / 'slide-up' , appear animation for the modal (optional, default 'slide-up')
});
```

 * **dismissModal(params = {})**

Dismiss the current modal.

```js
Navigation.dismissModal({
  animationType: 'slide-down' // 'none' / 'slide-down' , dismiss animation for the modal (optional, default 'slide-down')
});
```

## Screen API

This API is relevant when in a screen context - it allows a screen to push other screens, pop screens, change its navigator style, etc. Access to this API is available through the `navigator` object. When your screen components extend `Screen`, they have `this.navigator` available and initialized.

 * **push(params)**

Push a new screen into this screen's navigation stack.

```js
this.navigator.push({
  screen: 'example.ScreenThree', // unique ID registered with Navigation.registerScreen
  title: undefined, // navigation bar title of the pushed screen (optional)
  passProps: {}, // simple serializable object that will pass as props to the pushed screen (optional)
  animated: true, // does the push have transition animation or does it happen immediately (optional)
  backButtonTitle: undefined, // override the back button title (optional)
  navigatorStyle: {} // override the navigator style for the pushed screen (optional)
});
```

 * **pop(params = {})**

Pop the top screen from this screen's navigation stack.

```js
this.navigator.pop({
  animated: true // does the pop have transition animation or does it happen immediately (optional)
});
```

 * **showModal(params = {})**

Show a screen as a modal.
 
```js
this.navigator.showModal({
  screen: "example.ModalScreen", // unique ID registered with Navigation.registerScreen
  title: "Modal", // title of the screen as appears in the nav bar (optional)
  passProps: {}, // simple serializable object that will pass as props to the modal (optional)
  navigatorStyle: {}, // override the navigator style for the screen, see "Styling the navigator" below (optional)
  animationType: 'slide-up' // 'none' / 'slide-up' , appear animation for the modal (optional, default 'slide-up')
});
```

 * **dismissModal(params = {})**

Dismiss the current modal.

```js
this.navigator.dismissModal({
  animationType: 'slide-down' // 'none' / 'slide-down' , dismiss animation for the modal (optional, default 'slide-down')
});
```

## Styling the navigator

You can style the navigator appearance and behavior by passing a `navigatorStyle` object. This object can be passed when the screen is originally created; can be defined per-screen in the `static navigatorStyle = {};` on `Screen`; and can be overridden when a screen is pushed.

#### Style object format

```js
{
  navBarTextColor: '#000000', // change the text color of the title (remembered across pushes)
  navBarBackgroundColor: '#f7f7f7', // change the background color of the nav bar (remembered across pushes)
  navBarButtonColor: '#007aff', // change the button colors of the nav bar (eg. the back button) (remembered across pushes)
  navBarHidden: false, // make the nav bar hidden
  navBarHideOnScroll: false, // make the nav bar hidden only after the user starts to scroll
  navBarTranslucent: false, // make the nav bar semi-translucent, works best with drawUnderNavBar:true
  drawUnderNavBar: false, // draw the screen content under the nav bar, works best with navBarTranslucent:true
  drawUnderTabBar: false, // draw the screen content under the tab bar (the tab bar is always translucent)
  statusBarBlur: false, // blur the area under the status bar, works best with navBarHidden:true
  navBarBlur: false, // blur the entire nav bar, works best with drawUnderNavBar:true
  tabBarHidden: false, // make the screen content hide the tab bar (remembered across pushes)
  statusBarHideWithNavBar: false // hide the status bar if the nav bar is also hidden, useful for navBarHidden:true
  statusBarHidden: false, // make the status bar hidden regardless of nav bar state
  statusBarTextColorScheme: 'dark' // text color of status bar, 'dark' / 'light' (remembered across pushes)
}
```

> Note: If you set any styles related to the Status Bar, make sure that in Xcode > project > Info.plist, the property `View controller-based status bar appearance` is set to `YES`.

All supported styles are defined [here](https://github.com/wix/react-native-controllers#styling-navigation). There's also an example project there showcasing all the different styles.

#### Screen-specific style example

Define a screen-specific style by adding `static navigatorStyle = {};` to the Screen definition.

```js
class StyledScreen extends Screen {
  static navigatorStyle = {
    drawUnderNavBar: true,
    drawUnderTabBar: true,
    navBarTranslucent: true
  };
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <View style={{flex: 1}}>...</View>
     );
  }
```
