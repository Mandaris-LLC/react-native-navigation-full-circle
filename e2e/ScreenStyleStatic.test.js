const Utils = require('./Utils');
const elementByLabel = Utils.elementByLabel;

describe('screen style - static', () => {
  beforeEach(async () => {
    await device.relaunchApp();
  });

  it('declare a navigationOptions on container component', async () => {
    await elementByLabel('Push Options Screen').tap();
    await expect(element(by.label('Static Title').and(by.type('UILabel')))).toBeVisible();
  });
});
