import { LayoutTypes } from './LayoutTypes';

describe('LayoutTypes', () => {
  it('enum isValid', () => {
    expect(LayoutTypes.isValid('')).toBe(false);
    expect(LayoutTypes.isValid('unknown type')).toBe(false);
    expect(LayoutTypes.isValid('Component')).toBe(true);
    expect(LayoutTypes.isValid('Stack')).toBe(true);
    expect(LayoutTypes.isValid('Stack ')).toBe(false);
  });
});
