describe('app', () => {
  beforeEach((done) => {
    global.simulator.relaunchApp(done);
  });

  it('shows welcome screen', (done) => {
    expect(element(by.label('React Native Navigation!'))).toBeVisible();
    done();
  });
});
