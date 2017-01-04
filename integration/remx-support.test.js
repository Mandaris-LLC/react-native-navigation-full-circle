require('react-native');
import React from 'react';
import renderer from 'react-test-renderer';

describe('remx support', () => {
  let MyConnectedContainer;
  let store;

  beforeEach(() => {
    MyConnectedContainer = require('./remx-support-component').default;
    store = require('./remx-support-store');
  });

  it('renders normally', () => {
    const tree = renderer.create(<MyConnectedContainer/>);
    expect(tree.toJSON().children).toEqual(['no name']);
  });

  it('rerenders as a result of an underlying state change (by selector)', () => {
    const tree = renderer.create(<MyConnectedContainer/>);
    const instance = tree.getInstance();

    expect(tree.toJSON().children).toEqual(['no name']);
    expect(instance.renders).toEqual(1);

    store.mutators.setName('Bob');
    expect(store.selectors.getName()).toEqual('Bob');
    expect(tree.toJSON().children).toEqual(['Bob']);

    expect(instance.renders).toEqual(2);
  });

  it('rerenders as a result of an underlying state change with a new key using merge', () => {
    const tree = renderer.create(<MyConnectedContainer printAge={true}/>);
    const instance = tree.getInstance();

    expect(tree.toJSON().children).toEqual(null);
    expect(instance.renders).toEqual(1);

    store.mutators.setAge(30);
    expect(store.selectors.getAge()).toEqual(30);
    expect(tree.toJSON().children).toEqual([30]);

    expect(instance.renders).toEqual(2);
  });
});
