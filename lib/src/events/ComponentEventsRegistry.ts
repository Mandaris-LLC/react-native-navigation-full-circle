import { EventsRegistry } from './EventsRegistry';
import { Store } from '../components/Store';

export class ComponentEventsRegistry {
  constructor(private eventsRegistry: EventsRegistry, private store: Store) {
    this.componentDidAppear = this.componentDidAppear.bind(this);
    this.componentDidDisappear = this.componentDidDisappear.bind(this);
    this.onNavigationInteraction = this.onNavigationInteraction.bind(this);
  }

  public registerForAllComponents(): void {
    this.eventsRegistry.componentDidAppear(this.componentDidAppear);
    this.eventsRegistry.componentDidDisappear(this.componentDidDisappear);
    this.eventsRegistry.onNavigationInteraction(this.onNavigationInteraction);
  }

  private componentDidAppear(componentId: string) {
    const componentRef = this.store.getRefForId(componentId);
    if (componentRef && componentRef.componentDidAppear) {
      componentRef.componentDidAppear();
    }
  }

  private componentDidDisappear(componentId: string) {
    const componentRef = this.store.getRefForId(componentId);
    if (componentRef && componentRef.componentDidDisappear) {
      componentRef.componentDidDisappear();
    }
  }

  private onNavigationInteraction(componentId: string, params) {
    const componentRef = this.store.getRefForId(componentId);
    if (componentRef && componentRef.onNavigationInteraction) {
      componentRef.onNavigationInteraction(params);
    }
  }
}
