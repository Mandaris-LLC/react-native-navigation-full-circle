import * as Typedoc from 'typedoc';
import { OptionsReadMode } from 'typedoc/dist/lib/utils/options';
import * as fs from 'fs';

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

  constructor(tsconfigPath) {
    const tsconfig = JSON.parse(fs.readFileSync(tsconfigPath).toString());
    this.typedocApp = new Typedoc.Application({ ...OPTIONS, ...tsconfig.compilerOptions });
  }

  public read(rootPath: string): Typedoc.ProjectReflection {
    const expandedFiles = this.typedocApp.expandInputFiles([rootPath]);
    const projectReflection = this.typedocApp.convert(expandedFiles);
    // console.log(JSON.stringify(this.typedocApp.serializer.projectToObject(projectReflection)));
    return projectReflection;
  }
}
