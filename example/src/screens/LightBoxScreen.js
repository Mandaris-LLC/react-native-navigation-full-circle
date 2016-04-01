import React, {
  Component,
  Text,
  View,
  ScrollView,
  TouchableOpacity,
  StyleSheet
} from 'react-native';

export default class LightBoxScreen extends Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <View style={{width: 300, height: 200, padding: 20}}>

        <Text>
          This is a LightBox
        </Text>

        <TouchableOpacity onPress={ this.onDismissPress.bind(this) }>
          <Text style={styles.button}>Dismiss</Text>
        </TouchableOpacity>

      </View>
    );
  }
  onDismissPress() {
    this.props.navigator.dismissLightBox();
  }
}

const styles = StyleSheet.create({
  button: {
    textAlign: 'center',
    fontSize: 18,
    marginBottom: 10,
    marginTop:10,
    color: 'blue'
  }
});
