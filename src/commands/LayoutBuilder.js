import {uniqueId} from '../providers/UniqueIdProvider';

export function parse(params) {
  const layout = params;
  layout.container.id = uniqueId(`container`);
  if (layout.sideMenu) {
    if (layout.sideMenu.left) {
      layout.sideMenu.left.id = uniqueId(`container`);
    }
    if (layout.sideMenu.right) {
      layout.sideMenu.right.id = uniqueId(`container`);
    }
  }
  return layout;
}
