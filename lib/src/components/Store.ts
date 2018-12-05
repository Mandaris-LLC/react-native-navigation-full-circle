import * as _ from "lodash";

export class Store {
  public globalProps = {};
  private componentsByName = {};
  private propsById = {};

  setPropsForId(componentId: string, props) {
    _.set(this.propsById, componentId, props);
  }

  getPropsForId(componentId: string) {
    return Object.assign(
      {},
      _.get(this.propsById, componentId, {}),
      this.globalProps
    );
  }

  setComponentClassForName(componentName: string, ComponentClass) {
    _.set(this.componentsByName, componentName, ComponentClass);
  }

  getComponentClassForName(componentName: string) {
    return _.get(this.componentsByName, componentName);
  }

  cleanId(id: string) {
    _.unset(this.propsById, id);
  }
}
