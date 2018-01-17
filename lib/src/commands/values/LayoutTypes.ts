export class LayoutTypes {
  private static validTypes = [
    'Component',
    'Stack',
    'BottomTabs',
    'SideMenuRoot',
    'SideMenuCenter',
    'SideMenuLeft',
    'SideMenuRight',
    'TopTabs'];

  public static isValid(type: string) {
    return this.validTypes.includes(type);
  }
}
