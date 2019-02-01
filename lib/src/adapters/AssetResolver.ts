import { ImageRequireSource, Image } from 'react-native';

export class AssetService {
  resolveFromRequire(value: ImageRequireSource) {
    return (Image as any).resolveAssetSource(value);
  }
}
