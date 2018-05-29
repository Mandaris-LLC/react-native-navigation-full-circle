export class Constants {
  public readonly backButton;
  public readonly statusBarHeight;

  constructor(navigationModule) {
    this.backButton = navigationModule.backButton;
    this.statusBarHeight = navigationModule.statusBarHeight;
  }
}
