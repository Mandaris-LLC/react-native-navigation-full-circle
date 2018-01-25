import { LayoutNode } from './LayoutNode';
import { LayoutType } from './LayoutType';

describe('LayoutNode', () => {
  it('convertable from same data structure', () => {
    const x = {
      id: 'theId',
      type: LayoutType.Component,
      data: {},
      children: []
    };

    let got;
    function expectingLayoutNode(param: LayoutNode) {
      got = param;
    }
    expectingLayoutNode(x);

    expect(got).toBe(x);
  });
});
