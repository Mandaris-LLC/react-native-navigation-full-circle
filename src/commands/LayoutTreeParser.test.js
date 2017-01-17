xdescribe('LayoutTreeParser', () => {
  let LayoutBuilder;

  beforeEach(() => {
    jest.mock('../adapters/UniqueIdProvider');
    LayoutBuilder = require('./LayoutTreeParser');
  });

  describe('parse', () => {
    it('deeply clones input params', () => {
      const input = {inner: {value: 1}};
      expect(LayoutBuilder.parse(input)).not.toBe(input);
      expect(LayoutBuilder.parse(input).inner).not.toBe(input.inner);
    });

    it('adds uniqueId to passed container', () => {
      expect(LayoutBuilder.parse({
        container: {
          name: 'com.example.MyScreen'
        }
      })).toEqual({
        container: {
          name: 'com.example.MyScreen',
          id: 'containerUNIQUE'
        }
      });
    });

    it('adds uniqueId to passed sideMenu', () => {
      expect(LayoutBuilder.parse({
        container: {
          name: 'com.example.MyScreen'
        },
        sideMenu: {
          left: {
            name: 'com.example.SideMenu1'
          },
          right: {
            name: 'com.example.SideMenu2'
          }
        }
      })).toEqual({
        container: {
          name: 'com.example.MyScreen',
          id: 'containerUNIQUE'
        },
        sideMenu: {
          left: {
            name: 'com.example.SideMenu1',
            id: 'containerUNIQUE'
          },
          right: {
            name: 'com.example.SideMenu2',
            id: 'containerUNIQUE'
          }
        }
      });
    });

    it('adds uniqueId to passed tabs', () => {
      expect(LayoutBuilder.parse({
        tabs: [
          {
            container: {
              name: 'com.example.FirstTab'
            }
          },
          {
            container: {
              name: 'com.example.SecondTab'
            }
          },
          {
            container: {
              name: 'com.example.FirstTab'
            }
          }
        ],
        sideMenu: {
          left: {
            name: 'com.example.Menu1'
          },
          right: {
            name: 'com.example.Menu2'
          }
        }
      })).toEqual({
        tabs: [
          {
            container: {
              name: 'com.example.FirstTab',
              id: 'containerUNIQUE'
            }
          },
          {
            container: {
              name: 'com.example.SecondTab',
              id: 'containerUNIQUE'
            }
          },
          {
            container: {
              name: 'com.example.FirstTab',
              id: 'containerUNIQUE'
            }
          }
        ],
        sideMenu: {
          left: {
            name: 'com.example.Menu1',
            id: 'containerUNIQUE'
          },
          right: {
            name: 'com.example.Menu2',
            id: 'containerUNIQUE'
          }
        }
      });
    });
  });
});
