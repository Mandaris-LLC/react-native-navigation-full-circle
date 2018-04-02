# Top Level API

In order to make navigation API homogenous as much as possible, we provide setRoot function that receives layout of any kind.
See [Layout types](layout-types)


## setRoot(layout)

```js
Navigation.setRoot({
  bottomTabs: {
    children: [{
      stack: {
        children: [{
          component: {
            name: 'example.FirstTabScreen',
            passProps: {
              text: 'This is tab 1'
            }
          }
        }],
        options: {
          bottomTab: {
            title: 'Tab 1',
            icon: require('../images/one.png'),
            testID: 'FIRST_TAB_BAR_BUTTON'
          }
        }
      }
    },
    {
      component: {
        name: 'navigation.playground.TextScreen',
        passProps: {
          text: 'This is tab 2'
        },
        options: {
          bottomTab: {
            title: 'Tab 2',
            icon: require('../images/two.png'),
            testID: 'SECOND_TAB_BAR_BUTTON'
          }
        }
      }
    }]
  }
});
```

## showOverlay(layout = {})

Show component as overlay.

```js
Navigation.showOverlay({
  component: {
    name: 'example.Overlay',
    options: {
      overlay: {
        interceptTouchOutside: true
      }
    }
  }
});
```

## dismissOverlay(componentId)

Dismiss overlay matching the overlay componentId.

```js
Navigation.dismissOverlay(this.props.componentId);
```


<!-- ## handleDeepLink(params = {})

Trigger a deep link within the app. See [deep links](https://wix.github.io/react-native-navigation/#/deep-links) for more details about how screens can listen for deep link events.

```js
Navigation.handleDeepLink({
  link: 'link/in/any/format',
  payload: '' // (optional) Extra payload with deep link
});
``` -->

<!-- ## registerScreen(screenID, generator)

This is an internal function you probably don't want to use directly. If your screen components extend `Screen` directly (`import { Screen } from 'react-native-navigation'`), you can register them directly with `registerScreen` instead of with `registerComponent`. The main benefit of using `registerComponent` is that it wraps your regular screen component with a `Screen` automatically.

```js
Navigation.registerScreen('example.AdvancedScreen', () => AdvancedScreen);
```

## getCurrentlyVisibleScreenId()

In some cases you might need the id of the currently visible screen. This method returns the unique id of the currently visible screen:
`const visibleScreenInstanceId = await Navigation.getCurrentlyVisibleScreenId()`
In order to have any use of this method, you'd need to map instanceId to screens your self. -->