import * as _ from 'lodash';
import { processColor } from 'react-native';
import * as resolveAssetSource from 'react-native/Libraries/Image/resolveAssetSource';

export class OptionsProcessor {
  static processOptions(options: any) {
    _.forEach(options, (value, key) => {
      if (value) {
        if (_.endsWith(key, 'Color')) {
          options[key] = processColor(value);
        }
        if (_.isEqual(key, 'icon') || _.endsWith(key, 'Icon') || _.endsWith(key, 'Image')) {
          options[key] = resolveAssetSource(options[key]);
        }
        if (_.isObject(value) || _.isArray(value)) {
          OptionsProcessor.processOptions(value);
        }
      }
    });
  }
}
