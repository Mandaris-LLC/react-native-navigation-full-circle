import React, {
  Text,
  View,
  ScrollView,
  TouchableOpacity
} from 'react-native';

// important imports, the magic is here
import { Navigation, Screen } from 'react-native-navigation';

// instead of React.Component, we extend Screen (imported above)
class SecondTabScreen extends Screen {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <View style={{flex: 1, padding: 20}}>
        <Text>Second Tab Screen</Text>
      </View>
    );
  }
}

// every screen must be registered with a unique name
Navigation.registerScreen('example.SecondTabScreen', () => SecondTabScreen);
