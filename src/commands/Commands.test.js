describe('Commands', () => {
  let uut;
  const mockCommandsSender = {
    startApp: jest.fn()
  };
  const mockIdProvider = {
    generate: (prefix) => `${prefix}UNIQUE_ID`
  };

  beforeEach(() => {
    const Commands = require('./Commands').default;
    uut = new Commands(mockCommandsSender, mockIdProvider);
  });

  describe('startApp', () => {
    it('sends startApp to native after parsing into layoutTree', () => {
      uut.startApp({
        container: {
          name: 'com.example.MyScreen'
        }
      });
      expect(mockCommandsSender.startApp).toHaveBeenCalledTimes(1);
      expect(mockCommandsSender.startApp).toHaveBeenCalledWith({
        type: 'ContainerStack',
        id: 'ContainerStackUNIQUE_ID',
        children: [
          {
            type: 'Container',
            id: 'ContainerUNIQUE_ID',
            children: [],
            data: {
              name: 'com.example.MyScreen'
            }
          }
        ]
      });
    });
  });
});
