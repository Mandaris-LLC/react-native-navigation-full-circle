import { AppRegistry, ComponentProvider } from 'react-native';
import { ComponentWrapper } from './ComponentWrapper';

export class ComponentRegistry {
  private store;

  constructor(store) {
    this.store = store;
  }

  registerComponent(componentName: string, getComponentClassFunc: ComponentProvider): void {
    const OriginalComponentClass = getComponentClassFunc();
    const NavigationComponent = ComponentWrapper.wrap(componentName, OriginalComponentClass, this.store);
    this.store.setOriginalComponentClassForName(componentName, OriginalComponentClass);
    AppRegistry.registerComponent(componentName, () => NavigationComponent);
  }
}
