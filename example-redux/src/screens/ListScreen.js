import React, {Component, PropTypes} from 'react';
import {
  Text,
  View,
  ScrollView,
  TouchableOpacity,
  StyleSheet,
  ListView,
} from 'react-native';
import {connect} from 'react-redux';
import * as counterActions from '../reducers/counter/actions';

const LOREM_IPSUM = 'Lorem ipsum dolor sit amet, ius ad pertinax oportere accommodare, an vix civibus corrumpit referrentur. Te nam case ludus inciderint, te mea facilisi adipiscing. Sea id integre luptatum. In tota sale consequuntur nec. Erat ocurreret mei ei. Eu paulo sapientem vulputate est, vel an accusam intellegam interesset. Nam eu stet pericula reprimique, ea vim illud modus, putant invidunt reprehendunt ne qui.';
const hashCode = function(str) {
  var hash = 15;
  for (var ii = str.length - 1; ii >= 0; ii--) {
    hash = ((hash << 5) - hash) + str.charCodeAt(ii);
  }
  return hash;
};

class ListScreen extends Component {

  static navigatorStyle = {
    statusBarColor: '#7CB342',
    toolBarColor: '#8BC34A',
    navigationBarColor: '#8BC34A',
    drawUnderNavBar: true
  };

  constructor(props) {
    super(props);
    this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
    
    var ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
    this.state = {
      dataSource: ds.cloneWithRows(this._genRows({}))
    }
  }

  onNavigatorEvent(event) {
    if (event.type == 'DeepLink') {
      const parts = event.link.split('/');
      if (parts[0] == 'tab1' && parts[1] == 'pushScreen') {
        this.props.navigator.toggleDrawer({
          side: 'left',
          animated: true,
          to: 'closed'
        });

        this.props.navigator.push({
          title: "Pushed from SideMenu",
          screen: parts[2],
          passProps: {
            str: 'This is a prop passed in \'navigator.push()\'!',
            obj: {
              str: 'This is a prop passed in an object!',
              arr: [
                {
                  str: 'This is a prop in an object in an array in an object!'
                }
              ]
            },
            num: 1234
          }
        });
      }
    }
      console.log('ListScreen', 'Unhandled event ' + event.id);
  }

  render() {
    return (
      <ListView
        dataSource={this.state.dataSource}
        renderRow={this._renderRow}/>
    );
  }

  _renderRow(rowData, sectionID, rowID) {
    const rowHash = Math.abs(hashCode(rowData));
    return (
      <View style={styles.row}>
        <Text style={styles.text}>
          {rowData + ' - ' + LOREM_IPSUM.substr(0, rowHash % 301 + 10)}
        </Text>
      </View>
    );
  }

  _genRows() {
    var dataBlob = [];
    for (var ii = 0; ii < 100; ii++) {
      dataBlob.push('Row ' + ii + ' ');
    }
    return dataBlob;
  }

  _renderSeparator(sectionID, rowID) {
    return (
      <View
        key={`${sectionID}-${rowID}`}
        style={{
          height: 1,
          backgroundColor: rowID % 2 == 0 ? '#3B5998' : '#CCCCCC'
        }}
      />
    );
  }
}

export default connect()(ListScreen);

const styles = StyleSheet.create({
  row: {
    flexDirection: 'row',
    justifyContent: 'center',
    padding: 10,
    backgroundColor: '#F6F6F6'
  },
  thumb: {
    width: 64,
    height: 64
  },
  text: {
    flex: 1
  }
});


