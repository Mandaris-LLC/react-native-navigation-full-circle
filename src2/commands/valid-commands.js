export const singleScreenApp = {
  screen: {
    key: 'com.example.MyScreen'
  }
};

export const tabBasedApp = {
  tabs: [
    {
      screen: {
        key: 'com.example.FirstTab'
      }
    },
    {
      screen: {
        key: 'com.example.SecondTab'
      }
    },
    {
      screen: {
        key: 'com.example.ThirdTab'
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
        key: 'com.example.FirstTab'
      }
    },
    {
      screen: {
        key: 'com.example.SecondTab'
      }
    },
    {
      screen: {
        key: 'com.example.ThirdTab'
      }
    }
  ],
  sideMenu: {
    left: {
      key: 'com.example.Menu1'
    }
  }
};
