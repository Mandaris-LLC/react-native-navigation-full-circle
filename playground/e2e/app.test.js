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

  it('switch to tabs with side menus', () => {
    elementByLabel('Switch to app with side menus').tap();
    elementByLabel('This is a side menu center screen tab 1').swipe('right');
    expect(elementByLabel('This is a left side menu screen')).toBeVisible();
  });

  it('screen lifecycle', () => {
    elementByLabel('Push lifecycle screen').tap();
    expect(elementByLabel('onStart!')).toBeVisible();
    elementByLabel('Push to test onStop').tap();
    expect(elementByLabel('Alert')).toBeVisible();
    expect(elementByLabel('onStop')).toBeVisible();
  });

  it('unmount is called on pop', () => {
    elementByLabel('Push lifecycle screen').tap();
    expect(elementByLabel('onStart!')).toBeVisible();
    element(by.traits(['button']).and(by.label('Back'))).tap();
    expect(elementByLabel('onStop')).toBeVisible();
    expect(elementByLabel('componentWillUnmount')).toBeVisible();
  });
});

describe('screen stack', () => {
  beforeEach((done) => {
    global.simulator.relaunchApp(done);
  });

  it('push screen', () => {
    elementByLabel('Push').tap();
    expect(elementByLabel('Pushed Screen')).toBeVisible();
  });

  it('pop screen', () => {
    elementByLabel('Push').tap();
    expect(elementByLabel('Pushed Screen')).toBeVisible();
    elementByLabel('Pop').tap();
    expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('pop screen deep in the stack', () => {
    elementByLabel('Push').tap();
    expect(elementByLabel('Stack Position: 1')).toBeVisible();
    elementByLabel('Push').tap();
    expect(elementByLabel('Stack Position: 2')).toBeVisible();
    elementByLabel('Pop Previous').tap();
    expect(elementByLabel('Stack Position: 2')).toBeVisible();
    elementByLabel('Pop').tap();
    expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });
});

describe('modal', () => {
  beforeEach((done) => {
    global.simulator.relaunchApp(done);
  });

  it('show modal', () => {
    elementByLabel('Show Modal').tap();
    expect(elementByLabel('Modal Screen')).toBeVisible();
  });

  it('dismiss modal', () => {
    elementByLabel('Show Modal').tap();
    expect(elementByLabel('Modal Screen')).toBeVisible();
    elementByLabel('Dismiss Modal').tap();
    expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('show multiple modals', () => {
    elementByLabel('Show Modal').tap();
    expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    elementByLabel('Show Modal').tap();
    expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
    elementByLabel('Dismiss Modal').tap();
    expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    elementByLabel('Dismiss Modal').tap();
    expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('dismiss unknown screen id', () => {
    elementByLabel('Show Modal').tap();
    expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    elementByLabel('Dismiss Unknown Modal').tap();
    expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    elementByLabel('Dismiss Modal').tap();
    expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('dismiss modal by id which is not the top most', () => {
    elementByLabel('Show Modal').tap();
    expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    elementByLabel('Show Modal').tap();
    expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
    elementByLabel('Dismiss Previous Modal').tap();
    expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
    elementByLabel('Dismiss Modal').tap();
    expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('dismiss all previous modals by id when they are below top presented modal', () => {
    elementByLabel('Show Modal').tap();
    expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    elementByLabel('Show Modal').tap();
    expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
    elementByLabel('Show Modal').tap();
    expect(elementByLabel('Modal Stack Position: 3')).toBeVisible();

    elementByLabel('Dismiss ALL Previous Modals').tap();
    expect(elementByLabel('Modal Stack Position: 3')).toBeVisible();

    elementByLabel('Dismiss Modal').tap();
    expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('dismiss some modal by id deep in the stack', () => {
    elementByLabel('Show Modal').tap();
    expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    elementByLabel('Show Modal').tap();
    expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
    elementByLabel('Show Modal').tap();
    expect(elementByLabel('Modal Stack Position: 3')).toBeVisible();

    elementByLabel('Dismiss First In Stack').tap();
    expect(elementByLabel('Modal Stack Position: 3')).toBeVisible();

    elementByLabel('Dismiss Modal').tap();
    expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();

    elementByLabel('Dismiss Modal').tap();
    expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('dismissAllModals', () => {
    elementByLabel('Show Modal').tap();
    expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    elementByLabel('Show Modal').tap();
    expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
    elementByLabel('Dismiss All Modals').tap();
    expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });
});

describe('reload app', () => {
  beforeEach((done) => {
    simulator.reloadReactNativeApp(done);
  });

  it('push a screen to ensure its not there after reload', () => {
    elementByLabel('Push').tap();
    expect(elementByLabel('Pushed Screen')).toBeVisible();
  });

  it('show welcome screen after reload', () => {
    expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });
});

function elementByLabel(label) {
  return element(by.label(label));
}
