import _ from 'lodash';
import {uniqueId} from '../providers/UniqueIdProvider';

export function parse(params) {
  const layout = _.merge({}, params);
  if (layout.container) {
    layout.container.id = uniqueId(`container`);
  }
  if (layout.sideMenu) {
    if (layout.sideMenu.left) {
      layout.sideMenu.left.id = uniqueId(`container`);
    }
    if (layout.sideMenu.right) {
      layout.sideMenu.right.id = uniqueId(`container`);
    }
  }
  if (layout.tabs) {
    _.forEach(layout.tabs, (t) => t.container.id = uniqueId(`container`));
  }
  return layout;
}
