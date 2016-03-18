# Release Notes

### 0.1.0

* **Breaking change:** in order to handle nav button press events, you now need to register your event handler explicitly. Until now, you've just had to have `onNavigatorEvent(event)` method on your screen component. From now on, explicitly register your handler with `navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));`. A good place to do this is in your screen's constructor. See the example for an example.
* Simplified API significantly. You now don't need to have your screen components extend `Screen`. You can leave them extending React's traditional `Component`. When you register your screen, instead of using `Navigation.registerScreen` directly, you can now use `Navigation.registerComponent`. This wrapper will wrap your regular component with a `Screen` automatically and pass the navigator instance through props. The benefit of this change is that your screen components now have *zero(!)* references to react-native-navigation. All of the changes required by this package are now purely concentrated in one place - your app bootstrap.
* Added redux example and optional redux support
