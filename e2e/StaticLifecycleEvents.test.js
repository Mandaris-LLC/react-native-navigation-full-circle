const Utils = require('./Utils');
const testIDs = require('../playground/src/testIDs');
const { elementByLabel, elementById } = Utils;

describe('Overlay', () => {
  beforeEach(async () => {
    await device.relaunchApp();
  });

  it.skip('didAppear didDisappear', async () => {
    await elementById(testIDs.PUSH_STATIC_LIFECYCLE_BUTTON).tap();
    await expect(elementByLabel('Static Lifecycle Events')).toBeVisible();
    await expect(elementByLabel('didAppear | navigation.playground.StaticLifecycleOverlay')).toBeVisible();
    await elementByLabel('PUSH').tap();
    await expect(elementByLabel('didAppear | navigation.playground.PushedScreen')).toBeVisible();
    await expect(elementByLabel('didDisappear | navigation.playground.WelcomeScreen')).toBeVisible();
  });
});
