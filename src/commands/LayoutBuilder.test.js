describe('LayoutBuilder', () => {
  let uut;

  beforeEach(() => {
    jest.mock('../providers/UniqueIdProvider');
    uut = require('./LayoutBuilder');
  });

  describe('parse', () => {
    it('returns a new object', () => {
      const input = {};
      expect(uut.parse(input)).not.toBe(input);
    });

    it('adds uniqueId to passed sideMenu', () => {
      expect(uut.parse({
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
          id: 'container123'
        },
        sideMenu: {
          left: {
            key: 'com.example.SideMenu1',
            id: 'container123'
          },
          right: {
            key: 'com.example.SideMenu2',
            id: 'container123'
          }
        }
      });
    });
  });
});
