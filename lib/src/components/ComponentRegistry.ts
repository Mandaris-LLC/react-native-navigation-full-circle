import * as React from 'react';
import { AppRegistry } from 'react-native';
import {ComponentWrapper} from './ComponentWrapper';

export class ComponentRegistry {
  private store: any;

  constructor(store) {
    this.store = store;
  }

  registerComponent(componentName: string, getComponentClassFunc: ()=>React.Component) {
    const OriginalComponentClass = getComponentClassFunc();
    const NavigationComponent = ComponentWrapper.wrap(componentName, OriginalComponentClass, this.store);
    this.store.setOriginalComponentClassForName(componentName, OriginalComponentClass);
    AppRegistry.registerComponent(componentName, () => NavigationComponent);
  }
}
