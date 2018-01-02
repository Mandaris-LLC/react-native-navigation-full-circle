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
    name: 'MyReactComponent'
  }
};

const stackOfComponentsWithTopBar = {
  stack: [
    {
      component: {
        name: 'MyReactComponent1'
      }
    },

    {
      component: {
        name: 'MyReactComponent2',
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
        name: 'MyReactComponent1'
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
  ]
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
                stackOfComponentsWithTopBar,
                stackOfComponentsWithTopBar,
                {
                  topTabs: [
                    singleComponent,
                    singleComponent,
                    singleComponent,
                    singleComponent,
                    stackOfComponentsWithTopBar
                  ],
                  options: topBarOptions
                }
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
  stackOfComponentsWithTopBar,
  bottomTabs,
  sideMenu,
  topTabs,
  complexLayout
};
