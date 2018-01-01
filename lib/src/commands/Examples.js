const passProps = {
  strProp: 'string prop',
  numProp: 12345,
  objProp: { inner: { foo: 'bar' } },
  fnProp: () => 'Hello from a function'
};

const topBarOptions = {
  topBar: {
    title: 'Hello1'
  }
};

const singleComponent = {
  component: {
    name: 'myReactComponent'
  }
};

const stackOfComponentsWithTopBar = {
  stack: [
    {
      component: {
        name: 'myReactComponent1'
      }
    },

    {
      component: {
        name: 'myReactComponent2',
        options: topBarOptions // optional
      }
    }
  ]
};

const bottomTabs = {
  bottomTabs: [
    stackOfComponentsWithTopBar,
    stackOfComponentsWithTopBar,
    {
      component: {
        name: 'myReactComponent1'
      }
    }
  ]
};

const sideMenu = {
  sideMenu: {
    left: singleComponent,
    center: stackOfComponentsWithTopBar,
    right: singleComponent
  }
};

const topTabs = {
  topTabs: [
    singleComponent,
    singleComponent,
    singleComponent,
    singleComponent,
    stackOfComponentsWithTopBar
  ],
  options: topBarOptions
};

const complexLayout = {
  sideMenu: {
    left: singleComponent,
    center: {
      bottomTabs: [
        stackOfComponentsWithTopBar,
        stackOfComponentsWithTopBar,
        {
          stack: [
            {
              topTabs: [
                {
                  topTabs: [
                    singleComponent,
                    singleComponent,
                    singleComponent,
                    singleComponent,
                    stackOfComponentsWithTopBar
                  ],
                  options: topBarOptions
                },
                stackOfComponentsWithTopBar,
                stackOfComponentsWithTopBar
              ]
            }
          ]
        }
      ]
    }
  }
};

module.exports = {
  passProps,
  topBarOptions,
  singleComponent,
  bottomTabs,
  sideMenu,
  topTabs,
  complexLayout
};
