# React Native Navigation

### `Navigation`

App-wide support for 100% native navigation with potential isolation support.

#### Setup your demo project

This would normally go in your `index.ios.js`

```js
import { Navigation } from 'react-native-navigation';

// import the components for your root screens (or the packager will not bundle them)
// they all need to be registered with Navigation.registerScreen
import './FirstTabScreen';
import './SecondTabScreen';

// start the app
Navigation.startTabBasedApp([
  {
    title: 'One', // tab title
    screen: 'example.FirstTabScreen', // unique ID registered with Navigation.registerScreen
    icon: require('./img/one.png'), // local asset for tab icon (unselected state)
    selectedIcon: require('./img/one_selected.png'), // local asset for tab icon (selected state)
    screenTitle: 'Screen One', // navigation bar title
    navigatorStyle: {} // style the navigator for this screen (optional)
  },
  {
    title: 'Two',
    screen: 'example.SecondTabScreen',
    icon: require('./img/two.png'),
    selectedIcon: require('./img/two_selected.png'),
    screenTitle: 'Screen Two'
  }
]);
```

#### Slightly modify your screen components

Every screen that you want to be able to place in a tab, push to the navigation stack or present modally needs to follow two basic conventions:

1. Normally your React components extend `React.Component`, in order to get access to the `navigator` you need to extend `Screen` instead.

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

#### Navigation API (how to push and pop)

This API is available through the `navigator` object. When your screen components extend `Screen`, they have `this.navigator` available and initialized.

```js
this.navigator.push({
  screen: 'example.ScreenThree', // unique ID registered with Navigation.registerScreen
  title: undefined, // navigation bar title of the pushed screen (optional)
  passProps: {}, // simple serializable object that will pass as props to the pushed component (optional)
  animated: true, // does the push have transition animation or does it happen immediately (optional)
  backButtonTitle: undefined, // override the back button title (optional)
  navigatorStyle: {} // override the navigator style for the pushed screen (optional)
});

this.navigator.pop({
  animated: true // does the pop have transition animation or does it happen immediately (optional)
});
```

#### All types of apps you can create

* App based on several tabs (usually 2-5), a very common pattern in iOS (like Facebook app or the iOS Contacts app). Every tab has its own navigation stack with a native nav bar.

```js
Navigation.startTabBasedApp([
  {
    title: 'One', // tab title
    screen: 'example.FirstTabScreen', // unique ID registered with Navigation.registerScreen
    icon: require('./img/one.png'), // local asset for tab icon (unselected state)
    selectedIcon: require('./img/one_selected.png'), // local asset for tab icon (selected state)
    screenTitle: 'Screen One', // navigation bar title
    navigatorStyle: {} // style the navigator for this screen (optional)
  },
  {
    title: 'Two',
    screen: 'example.SecondTabScreen',
    icon: require('./img/two.png'),
    selectedIcon: require('./img/two_selected.png'),
    screenTitle: 'Screen Two'
  }
]);
```

* App based on a single screen (like the iOS Calendar or Settings app). The screen will receive its own navigation stack with a native nav bar.

```js
Navigation.startSingleScreenApp({
  screen: 'example.WelcomeScreen', // unique ID registered with Navigation.registerScreen
  screenTitle: 'Welcome', // navigation bar title
  navigatorStyle: {} // style the navigator for this screen (optional)
});
```

It is also possible to switch between types of apps while the app is running. This can be useful for example when switching from a login mode (which has no tabs = `startSingleScreenApp`) to the actual app itself (which has tabs = `startTabBasedApp`). Please note that when switching formats, the entire "old" app will be unmounted and released.

> Tip: The other pattern of implementing login is having just one app type (like tabs) and showing the login dialog as a modal that hides the tabs when the app is launched. When login is completed, this modal is dismissed.

#### Styling the navigator

You can style the navigator appearance and behavior by passing a `navigatorStyle` object. This object can be passed when the screen is originally created; can be defined per-screen in the `static navigatorStyle = {};` on `Screen`; and can be overridden when a screen is pushed.

All supported styles are defined [here](https://github.com/wix/react-native-controllers#styling-navigation).
