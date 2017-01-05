describe('app', () => {
  beforeEach((done) => {
    global.simulator.relaunchApp(done);
  });

  it('shows welcome screen', () => {
    expect(element(by.label('React Native Navigation!'))).toBeVisible();
  });
});
