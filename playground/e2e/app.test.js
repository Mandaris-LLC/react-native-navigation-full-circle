describe('app', () => {
  beforeEach((done) => {
    global.simulator.relaunchApp(done);
  });

  it('shows welcome screen', () => {
    expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('switch to tab based app', () => {
    elementById('btnSwitchToTabs').tap();
    expect(elementByLabel('This is a tab screen')).toBeVisible();
  });

  it('switch to tabs with side menus', () => {
    elementById('btnSwitchToTabsWithMenus').tap();
    // expect(elementByLabel('This is a tab screen')).toBeVisible();
  });
});

function elementByLabel(label) {
  return element(by.label(label));
}

function elementById(id) {
  return element(by.id(id));
}
