import * as _ from 'lodash';
import { processColor } from 'react-native';
import * as resolveAssetSource from 'react-native/Libraries/Image/resolveAssetSource';

export class OptionsProcessor {
  public processOptions(options, store) {
    _.forEach(options, (value, key) => {
      if (!value) { return; }

      if (_.isEqual(key, 'color') || _.endsWith(key, 'Color')) {
        options[key] = processColor(value);
      }

      if (_.isEqual(key, 'icon') || _.isEqual(key, 'image') || _.endsWith(key, 'Icon') || _.endsWith(key, 'Image')) {
        options[key] = resolveAssetSource(options[key]);
      }

      if (_.endsWith(key, 'Buttons')) {
        this.saveButtonsPassProps(value, store);
      }

      if (_.isObject(value) || _.isArray(value)) {
        this.processOptions(value, store);
      }
    });
  }

  private saveButtonsPassProps(array, store) {
    _.forEach(array, (value) => {
      if (value.passProps && value.id) {
        store.setPropsForId(value.id, value.passProps);
      }
    });
  }
}
