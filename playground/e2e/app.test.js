describe('app', () => {
  beforeEach((done) => {
    global.simulator.relaunchApp(done);
  });

  it('shows welcome screen', () => {
    expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('switch to tab based app, passProps and functions', () => {
    elementByLabel('Switch to tab based app').tap();
    expect(elementByLabel('This is tab 1')).toBeVisible();
    expect(elementByLabel('Hello from a function!')).toBeVisible();
  });

  it('push screen', () => {
    elementByLabel('Push').tap();
    expect(elementByLabel('Pushed screen')).toBeVisible();
  });

  xit('switch to tabs with side menus', () => {
    elementByLabel('Switch to tab based app with side menus').tap();
    elementByLabel('Switch to tab based app with side menus').swipeRight();
    expect(elementByLabel('This is a side menu screen')).toBeVisible();
  });

  it('screen lifecycle', () => {
    elementByLabel('Switch to lifecycle screen').tap();
    expect(elementByLabel('onStart!')).toBeVisible();
    elementByLabel('Push to test onStop').tap();
    expect(elementByLabel('Alert')).toBeVisible();
    expect(elementByLabel('onStop!')).toBeVisible();
  });

  it('pop screen', () => {
    elementByLabel('Push').tap();
    expect(elementByLabel('Pushed screen')).toBeVisible();
    elementByLabel('Pop').tap();
    expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });
});

function elementByLabel(label) {
  return element(by.label(label));
}
