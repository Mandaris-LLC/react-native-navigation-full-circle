import * as fs from 'fs';
import { ReflectionsReader } from './ReflectionsReader';
import { ClassParser } from './ClassParser';
import { MarkdownWriter } from './MarkdownWriter';
import { ReflectionKind } from 'typedoc';

const INPUT_DIR = `${__dirname}/../../lib/src`;
const OUTPUT_DIR = `${__dirname}/../../docs/api`;
const TEMPLATES_DIR = `${__dirname}/templates`;
const TSCONFIG_PATH = `${__dirname}/../../tsconfig.json`;
const SOURCE_LINK_PREFIX = `https://github.com/wix/react-native-navigation/blob/v2/lib/src`;

class Main {
  public run() {
    const classParser = new ClassParser(SOURCE_LINK_PREFIX);
    const markdownWriter = new MarkdownWriter(TEMPLATES_DIR, OUTPUT_DIR);
    const projectReflections = new ReflectionsReader(TSCONFIG_PATH).read(INPUT_DIR);

    const externalModules = projectReflections.getChildrenByKind(ReflectionKind.ExternalModule)
      .filter((m) => !m.name.endsWith('.mock"') && !m.name.endsWith('.test"'));

    const classReflections = externalModules.filter((m) => m.getChildrenByKind(ReflectionKind.Class).length === 1)
      .map((m) => m.getChildrenByKind(ReflectionKind.Class)[0]);
    // just class modules, TODO: extract interfaces and types to their own modules, generate docs for interfaces and types

    const parsedClasses = classReflections.map((c) => classParser.parseClass(c));
    markdownWriter.writeClasses(parsedClasses);
    markdownWriter.writeMenu(parsedClasses);
  }
}

new Main().run();
