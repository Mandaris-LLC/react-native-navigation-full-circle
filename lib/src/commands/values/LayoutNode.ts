import { LayoutType } from "./LayoutType";

export interface LayoutNode {
  id: string;
  type: LayoutType;
  data: object;
  children: Array<LayoutNode>;
}

