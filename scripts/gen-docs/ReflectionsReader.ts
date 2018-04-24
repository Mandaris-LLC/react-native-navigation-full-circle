import * as Typedoc from 'typedoc';
import { OptionsReadMode } from 'typedoc/dist/lib/utils/options';

const OPTIONS = {
  excludeExternals: true,
  excludePrivate: true,
  includeDeclarations: true,
  mode: 'modules',
  module: 'commonjs',
  readme: 'none',
  target: 'ES6'
};

export class ReflectionsReader {
  private typedocApp: Typedoc.Application;

  constructor(tsconfig) {
    this.typedocApp = new Typedoc.Application({ ...OPTIONS, ...tsconfig.compilerOptions });
  }

  public read(modulePathRoot: string): Typedoc.DeclarationReflection {
    const expandedFiles = this.typedocApp.expandInputFiles([modulePathRoot]);
    const projectReflection = this.typedocApp.convert(expandedFiles);
    // console.log(JSON.stringify(this.typedocApp.serializer.projectToObject(projectReflection)));
    const externalModule = projectReflection.getChildrenByKind(Typedoc.ReflectionKind.ExternalModule)[0];
    const classReflection = externalModule.getChildrenByKind(Typedoc.ReflectionKind.Class)[0];
    return classReflection;
  }
}
