const Root = require('./Root');

const COMPONENT = { name: 'myScreen' };
const SIDE_MENU = {
  left: {
    component: {
      name: 'navigation.playground.TextScreen',
      passProps: {
        text: 'This is a left side menu screen'
      }
    }
  },
  right: {
    component: {
      name: 'navigation.playground.TextScreen',
      passProps: {
        text: 'This is a right side menu screen'
      }
    }
  }
};
const BOTTOM_TABS = [
  {
    component: {
      name: 'navigation.playground.TextScreen',
      passProps: {
        text: 'This is a side menu center screen tab 1'
      }
    }
  },
  {
    component: {
      name: 'navigation.playground.TextScreen',
      passProps: {
        text: 'This is a side menu center screen tab 2'
      }
    }
  },
  {
    component: {
      name: 'navigation.playground.TextScreen',
      passProps: {
        text: 'This is a side menu center screen tab 3'
      }
    }
  }
];

describe('Root', () => {
  it('Parses Root', () => {
    const uut = new Root(simpleRoot());
    expect(uut.component.name).toEqual(COMPONENT.name);
  });

  it('parses root with sideMenu', () => {
    const uut = new Root(rootWithSideMenu());
    expect(uut.component.name).toEqual(COMPONENT.name);
    expect(uut.sideMenu).toEqual(SIDE_MENU);
  });

  it('parses root with BottomTabs', () => {
    const uut = new Root(rootWithBottomTabs());
    expect(uut.bottomTabs).toEqual(BOTTOM_TABS);
  });
});

function rootWithBottomTabs() {
  return {
    component: COMPONENT,
    bottomTabs: BOTTOM_TABS
  };
}

function simpleRoot() {
  return { component: COMPONENT };
}

function rootWithSideMenu() {
  return {
    component: COMPONENT,
    sideMenu: SIDE_MENU
  };
}
