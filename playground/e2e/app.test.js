describe('app', () => {
  beforeEach((done) => {
    global.simulator.relaunchApp(done);
  });

  it('shows welcome screen', () => {
    expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('switch to tab based app', () => {
    elementByLabel('Switch to tab based app').tap();
    expect(elementByLabel('This is a tab screen')).toBeVisible();
  });

  xit('switch to tabs with side menus', () => {
    elementByLabel('Switch to tab based app with side menus').tap();
    expect(elementByLabel('This is a tab screen')).toBeVisible();
  });
});

function elementByLabel(label) {
  return element(by.label(label));
}
