describe('test environment', () => {
  it('handles object spread', () => {
    const { x, y, ...z } = { x: 1, y: 2, a: 3, b: 4 };
    expect(x).toEqual(1);
    expect(y).toEqual(2);
    expect(z).toEqual({ a: 3, b: 4 });
  });

  it('handles async await', async () => {
    const result = await new Promise((r) => r('hello'));
    expect(result).toEqual('hello');
  });
});
