import * as _ from 'lodash';

export const enum LayoutType {
  Component = 'Component',
  Stack = 'Stack',
  BottomTabs = 'BottomTabs',
  SideMenuRoot = 'SideMenuRoot',
  SideMenuCenter = 'SideMenuCenter',
  SideMenuLeft = 'SideMenuLeft',
  SideMenuRight = 'SideMenuRight',
  TopTabs = 'TopTabs'
};

export function isValid(name: string) {
  return _.keys(LayoutType);
}
