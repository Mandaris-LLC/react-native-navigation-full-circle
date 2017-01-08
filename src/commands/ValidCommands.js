export const singleScreenApp = {
  container: {
    key: 'com.example.MyScreen'
  }
};

export const tabBasedApp = {
  tabs: [
    {
      container: {
        key: 'com.example.FirstTab'
      }
    },
    {
      container: {
        key: 'com.example.SecondTab'
      }
    },
    {
      container: {
        key: 'com.example.FirstTab'
      }
    }
  ]
};

export const singleWithSideMenu = {
  container: {
    key: 'com.example.MyScreen'
  },
  sideMenu: {
    left: {
      key: 'com.example.Menu'
    }
  }
};

export const singleWithRightSideMenu = {
  container: {
    key: 'com.example.MyScreen'
  },
  sideMenu: {
    right: {
      key: 'com.example.Menu'
    }
  }
};

export const singleWithBothMenus = {
  container: {
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
      container: {
        key: 'com.example.FirstTab'
      }
    },
    {
      container: {
        key: 'com.example.SecondTab'
      }
    },
    {
      container: {
        key: 'com.example.FirstTab'
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
