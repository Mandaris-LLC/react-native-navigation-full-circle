describe('app', function() {
  beforeEach(function(done) {
    global.simulator.relaunchApp(done);
  });

  it('shows welcome screen', function() {
    expect(element(by.label('React Native Navigation!'))).toBeVisible();
  });
});
