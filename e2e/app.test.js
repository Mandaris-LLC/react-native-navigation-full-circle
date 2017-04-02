describe('top level api', () => {
  beforeEach(async () => {
    await device.relaunchApp();
  });

  it('shows welcome screen', async () => {
    await expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('switch to tab based app, passProps and functions', async () => {
    await elementByLabel('Switch to tab based app').tap();
    await expect(elementByLabel('This is tab 1')).toBeVisible();
    await expect(elementByLabel('Hello from a function!')).toBeVisible();
  });

  it('switch to tabs with side menus', async () => {
    await elementByLabel('Switch to app with side menus').tap();
    await elementByLabel('This is a side menu center screen tab 1').swipe('right');
    await expect(elementByLabel('This is a left side menu screen')).toBeVisible();
  });

  it('screen lifecycle', async () => {
    await elementByLabel('Push lifecycle screen').tap();
    await expect(elementByLabel('onStart!')).toBeVisible();
    await elementByLabel('Push to test onStop').tap();
    await expect(elementByLabel('Alert')).toBeVisible();
    await expect(elementByLabel('onStop')).toBeVisible();
  });

  it('unmount is called on pop', async () => {
    await elementByLabel('Push lifecycle screen').tap();
    await expect(elementByLabel('onStart!')).toBeVisible();
    await element(by.traits(['button']).and(by.label('Back'))).tap();
    await expect(elementByLabel('onStop')).toBeVisible();
    await expect(elementByLabel('componentWillUnmount')).toBeVisible();
  });
});

describe('screen stack', () => {
  beforeEach(async () => {
    await device.relaunchApp();
  });

  it('push screen', async () => {
    await elementByLabel('Push').tap();
    await expect(elementByLabel('Pushed Screen')).toBeVisible();
  });

  it('pop screen', async () => {
    await elementByLabel('Push').tap();
    await expect(elementByLabel('Pushed Screen')).toBeVisible();
    await elementByLabel('Pop').tap();
    await expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('pop screen deep in the stack', async () => {
    await elementByLabel('Push').tap();
    await expect(elementByLabel('Stack Position: 1')).toBeVisible();
    await elementByLabel('Push').tap();
    await expect(elementByLabel('Stack Position: 2')).toBeVisible();
    await elementByLabel('Pop Previous').tap();
    await expect(elementByLabel('Stack Position: 2')).toBeVisible();
    await elementByLabel('Pop').tap();
    await expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('pop to specific id', async () => {
    await elementByLabel('Push').tap();
    await elementByLabel('Push').tap();
    await elementByLabel('Push').tap();
    await expect(elementByLabel('Stack Position: 3')).toBeVisible();
    await elementByLabel('Pop To Stack Position 1').tap();
    await expect(elementByLabel('Stack Position: 1')).toBeVisible();
  });

  it('pop to root', async () => {
    await elementByLabel('Push').tap();
    await elementByLabel('Push').tap();
    await elementByLabel('Push').tap();
    await elementByLabel('Pop To Root').tap();
    await expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });
});

describe('modal', () => {
  beforeEach(async () => {
    await device.relaunchApp();
  });

  it('show modal', async () => {
    await elementByLabel('Show Modal').tap();
    await expect(elementByLabel('Modal Screen')).toBeVisible();
  });

  it('dismiss modal', async () => {
    await elementByLabel('Show Modal').tap();
    await expect(elementByLabel('Modal Screen')).toBeVisible();
    await elementByLabel('Dismiss Modal').tap();
    await expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('show multiple modals', async () => {
    await elementByLabel('Show Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    await elementByLabel('Show Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
    await elementByLabel('Dismiss Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    await elementByLabel('Dismiss Modal').tap();
    await expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('dismiss unknown screen id', async () => {
    await elementByLabel('Show Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    await elementByLabel('Dismiss Unknown Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    await elementByLabel('Dismiss Modal').tap();
    await expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('dismiss modal by id which is not the top most', async () => {
    await elementByLabel('Show Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    await elementByLabel('Show Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
    await elementByLabel('Dismiss Previous Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
    await elementByLabel('Dismiss Modal').tap();
    await expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('dismiss all previous modals by id when they are below top presented modal', async () => {
    await elementByLabel('Show Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    await elementByLabel('Show Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
    await elementByLabel('Show Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 3')).toBeVisible();

    await elementByLabel('Dismiss ALL Previous Modals').tap();
    await expect(elementByLabel('Modal Stack Position: 3')).toBeVisible();

    await elementByLabel('Dismiss Modal').tap();
    await expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('dismiss some modal by id deep in the stack', async () => {
    await elementByLabel('Show Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    await elementByLabel('Show Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
    await elementByLabel('Show Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 3')).toBeVisible();

    await elementByLabel('Dismiss First In Stack').tap();
    await expect(elementByLabel('Modal Stack Position: 3')).toBeVisible();

    await elementByLabel('Dismiss Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();

    await elementByLabel('Dismiss Modal').tap();
    await expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });

  it('dismissAllModals', async () => {
    await elementByLabel('Show Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 1')).toBeVisible();
    await elementByLabel('Show Modal').tap();
    await expect(elementByLabel('Modal Stack Position: 2')).toBeVisible();
    await elementByLabel('Dismiss All Modals').tap();
    await expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });
});

describe('reload app', async () => {
  beforeEach(async () => {
    await device.relaunchApp();
  });

  it('push a screen to ensure its not there after reload', async () => {
    await elementByLabel('Push').tap();
    await expect(elementByLabel('Pushed Screen')).toBeVisible();
    await device.reloadReactNative();
    await expect(elementByLabel('React Native Navigation!')).toBeVisible();
  });
});

describe('screen style - static', () => {
  beforeEach(async () => {
    await device.relaunchApp();
  });

  it('declare a navigationStyle on container component', async () => {
    await elementByLabel('Push').tap();
    await expect(element(by.label('Static Title').and.by.type('UILabel'))).toBeVisible();
  });
});

function elementByLabel(label) {
  return element(by.label(label));
}
