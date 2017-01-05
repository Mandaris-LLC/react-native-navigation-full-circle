describe('valid commands', () => {
  it('just works', () => {
    //
  });
});

const singleContainerApp = {
  container: {
    key: 'com.example.FirstTabContainer'
  }
};

const tabBasedApp = {
  tabs: [
    {
      container: {
        key: 'com.example.FirstTabContainer'
      }
    },
    {
      container: {
        key: 'com.example.SecondTabContainer'
      }
    },
    {
      container: {
        key: 'com.example.FirstTabContainer'
      }
    }
  ]
};

const singleWithSideMenu = {
  container: {
    key: 'com.example.MyContainer'
  },
  sideMenu: {
    left: {
      key: 'com.example.Menu'
    }
  }
};

const singleWithRightSideMenu = {
  container: {
    key: 'com.example.MyContainer'
  },
  sideMenu: {
    right: {
      key: 'com.example.Menu'
    }
  }
};

const singleWithBothMenus = {
  container: {
    key: 'com.example.MyContainer'
  },
  sideMenu: {
    left: {
      key: 'com.example.Menu1'
    },
    right: {
      key: 'com.example.Menu2'
    }
  }
};

const tabBasedWithSideMenu = {
  tabs: [
    {
      container: {
        key: 'com.example.FirstTabContainer'
      }
    },
    {
      container: {
        key: 'com.example.SecondTabContainer'
      }
    },
    {
      container: {
        key: 'com.example.FirstTabContainer'
      }
    }
  ],
  sideMenu: {
    left: {
      key: 'com.example.Menu1'
    },
    right: {
      key: 'com.example.Menu2'
    }
  }
};
