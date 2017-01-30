describe('Commands', () => {
  let uut;
  const mockCommandsSender = {
    setRoot: jest.fn()
  };
  const mockIdProvider = {
    generate: (prefix) => `${prefix}UNIQUE_ID`
  };

  beforeEach(() => {
    const Commands = require('./Commands').default;
    uut = new Commands(mockCommandsSender, mockIdProvider);
  });

  describe('setRoot', () => {
    it('sends setRoot to native after parsing into layoutTree', () => {
      uut.setRoot({
        container: {
          name: 'com.example.MyScreen'
        }
      });
      expect(mockCommandsSender.setRoot).toHaveBeenCalledTimes(1);
      expect(mockCommandsSender.setRoot).toHaveBeenCalledWith({
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
