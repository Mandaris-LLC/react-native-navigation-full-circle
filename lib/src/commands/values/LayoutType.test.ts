import { LayoutType } from './LayoutType';

describe('LayoutType', () => {
  it('is an enum', () => {
    expect(LayoutType.Component).toEqual('Component');
    expect(LayoutType.Stack).toEqual('Stack');
  });

  it('valueOf dynamic string value', () => {
    const name = 'Stack';
    expect(LayoutType[name]).toEqual(LayoutType.Stack);
    expect(LayoutType['asdasd']).toEqual(undefined);
    expect(LayoutType.isValid('asdasd')).toBe(false);
    expect(LayoutType.isValid('TopTabs')).toBe(true);
    expect(LayoutType.isValid('isValid')).toBe(false);
  });
});

// Component: 'Component',
// Stack: 'Stack',
// BottomTabs: 'BottomTabs',
// SideMenuRoot: 'SideMenuRoot',
// SideMenuCenter: 'SideMenuCenter',
// SideMenuLeft: 'SideMenuLeft',
// SideMenuRight: 'SideMenuRight',
// TopTabs: 'TopTabs',
