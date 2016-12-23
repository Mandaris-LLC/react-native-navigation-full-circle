export const singleScreenApp = {
  screen: {
    key: 'com.example.FirstTabScreen'
  }
};

export const tabBasedApp = {
  tabs: [
    {
      screen: {
        key: 'com.example.FirstTabScreen'
      }
    },
    {
      screen: {
        key: 'com.example.SecondTabScreen'
      }
    },
    {
      screen: {
        key: 'com.example.FirstTabScreen'
      }
    }
  ]
};

export const singleWithSideMenu = {
  screen: {
    key: 'com.example.MyScreen'
  },
  sideMenu: {
    left: {
      key: 'com.example.Menu'
    }
  }
};

export const singleWithRightSideMenu = {
  screen: {
    key: 'com.example.MyScreen'
  },
  sideMenu: {
    right: {
      key: 'com.example.Menu'
    }
  }
};

export const singleWithBothMenus = {
  screen: {
    key: 'com.example.MyScreen'
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
      screen: {
        key: 'com.example.FirstTabScreen'
      }
    },
    {
      screen: {
        key: 'com.example.SecondTabScreen'
      }
    },
    {
      screen: {
        key: 'com.example.FirstTabScreen'
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
