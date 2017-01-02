export const singleContainerApp = {
  container: {
    key: 'com.example.FirstTabContainer'
  }
};

export const tabBasedApp = {
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

export const singleWithSideMenu = {
  container: {
    key: 'com.example.MyContainer'
  },
  sideMenu: {
    left: {
      key: 'com.example.Menu'
    }
  }
};

export const singleWithRightSideMenu = {
  container: {
    key: 'com.example.MyContainer'
  },
  sideMenu: {
    right: {
      key: 'com.example.Menu'
    }
  }
};

export const singleWithBothMenus = {
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

export const tabBasedWithSideMenu = {
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
