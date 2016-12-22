describe('ScreenStore', () => {
  let uut;

  beforeEach(() => {
    uut = require('./ScreenStore');
  });

  class Component {
    //
  }
  class MyComponent extends Component {
    //
  }

  it('holds screens classes by screenKey', () => {
    uut.saveScreenClass('example.mykey', MyComponent);
    expect(uut.getScreenClass('example.mykey')).toEqual(MyComponent);
  });
});
