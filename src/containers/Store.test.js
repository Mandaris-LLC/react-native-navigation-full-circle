describe('Store', () => {
  let uut;

  beforeEach(() => {
    const Store = require('./Store').default;
    uut = new Store();
  });

  it('initial state', () => {
    expect(uut.getPropsForContainerId('container1')).toEqual({});
  });

  it('holds props by containerId', () => {
    uut.setPropsForContainerId('container1', { a: 1, b: 2 });
    expect(uut.getPropsForContainerId('container1')).toEqual({ a: 1, b: 2 });
  });

  it('defensive for invalid containerId and props', () => {
    uut.setPropsForContainerId('container1', undefined);
    uut.setPropsForContainerId(undefined, undefined);
    expect(uut.getPropsForContainerId('container1')).toEqual({});
  });

  it('holds containers classes by containerName', () => {
    const MyComponent = class {
      //
    };
    uut.setContainerClass('example.mycontainer', MyComponent);
    expect(uut.getContainerClass('example.mycontainer')).toEqual(MyComponent);
  });
});

