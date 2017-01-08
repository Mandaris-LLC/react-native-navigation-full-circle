describe('LayoutBuilder', () => {
  let LayoutBuilder;

  beforeEach(() => {
    jest.mock('../providers/UniqueIdProvider');
    LayoutBuilder = require('./LayoutBuilder');
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
          key: 'com.example.MyScreen'
        }
      })).toEqual({
        container: {
          key: 'com.example.MyScreen',
          id: 'containerUNIQUE'
        }
      });
    });

    it('adds uniqueId to passed sideMenu', () => {
      expect(LayoutBuilder.parse({
        container: {
          key: 'com.example.MyScreen'
        },
        sideMenu: {
          left: {
            key: 'com.example.SideMenu1'
          },
          right: {
            key: 'com.example.SideMenu2'
          }
        }
      })).toEqual({
        container: {
          key: 'com.example.MyScreen',
          id: 'containerUNIQUE'
        },
        sideMenu: {
          left: {
            key: 'com.example.SideMenu1',
            id: 'containerUNIQUE'
          },
          right: {
            key: 'com.example.SideMenu2',
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
      })).toEqual({
        tabs: [
          {
            container: {
              key: 'com.example.FirstTab',
              id: 'containerUNIQUE'
            }
          },
          {
            container: {
              key: 'com.example.SecondTab',
              id: 'containerUNIQUE'
            }
          },
          {
            container: {
              key: 'com.example.FirstTab',
              id: 'containerUNIQUE'
            }
          }
        ],
        sideMenu: {
          left: {
            key: 'com.example.Menu1',
            id: 'containerUNIQUE'
          },
          right: {
            key: 'com.example.Menu2',
            id: 'containerUNIQUE'
          }
        }
      });
    });
  });
});
