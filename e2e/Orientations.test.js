
const Utils = require('./Utils');

const elementByLabel = Utils.elementByLabel;

describe('orientation', () => {
  beforeEach(async () => {
    await device.relaunchApp();
  });

  it('orientation should not change from landscape', async () => {
    await elementByLabel('Orientation').tap();
    await device.setOrientation('landscape');
    await elementByLabel('Push landscape only screen').tap();
    await device.setOrientation('portrait');
    await expect(element(by.id('currentOrientation'))).toHaveText('Landscape');
  });

  it('orientation should not change from portrait', async () => {
    await elementByLabel('Orientation').tap();
    await device.setOrientation('portrait');
    await elementByLabel('Push portrait only screen').tap();
    await device.setOrientation('landscape');
    await expect(element(by.id('currentOrientation'))).toHaveText('Portrait');
  });

  it('orientation should change to portrait and landscape', async () => {
    await elementByLabel('Orientation').tap();
    await device.setOrientation('portrait');
    await elementByLabel('Push landscape and portrait').tap();
    await device.setOrientation('landscape');
    await expect(element(by.id('currentOrientation'))).toHaveText('Landscape');
    await device.setOrientation('portrait');
    await expect(element(by.id('currentOrientation'))).toHaveText('Portrait');
  });

  it('orientation default should change to portrait and landscape', async () => {
    await elementByLabel('Orientation').tap();
    await device.setOrientation('portrait');
    await elementByLabel('Push default').tap();
    await device.setOrientation('landscape');
    await expect(element(by.id('currentOrientation'))).toHaveText('Landscape');
    await device.setOrientation('portrait');
    await expect(element(by.id('currentOrientation'))).toHaveText('Portrait');
  });
});
