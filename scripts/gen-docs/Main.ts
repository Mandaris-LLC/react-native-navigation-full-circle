import * as fs from 'fs';
import { ReflectionsReader } from './ReflectionsReader';
import { MarkdownCreator } from './MarkdownCreator';
import * as Handlebars from 'handlebars';

const INPUT_DIR = `${__dirname}/../../lib/src/Navigation.ts`;
const OUTPUT_DIR = `${__dirname}/../../docs/api`;
const SOURCE_LINK_PREFIX = `https://github.com/wix/react-native-navigation/blob/v2/lib/src`;
const TEMPLATES_DIR = `${__dirname}/templates`;
const TSCONFIG = JSON.parse(fs.readFileSync(`${__dirname}/../../tsconfig.json`).toString());

class Main {
  public run() {
    const handlebarsFn = this.setupHandlebars();

    const reflection = new ReflectionsReader(TSCONFIG).read(INPUT_DIR);
    const markdown = new MarkdownCreator(SOURCE_LINK_PREFIX, handlebarsFn).create(reflection);

    fs.writeFileSync(`${OUTPUT_DIR}/Navigation.md`, markdown, { encoding: 'utf8' });
  }

  private setupHandlebars() {
    const classTemplate = fs.readFileSync(`${TEMPLATES_DIR}/class.hbs`).toString();
    const methodTemplate = fs.readFileSync(`${TEMPLATES_DIR}/method.hbs`).toString();
    const propertyTemplate = fs.readFileSync(`${TEMPLATES_DIR}/property.hbs`).toString();

    Handlebars.registerPartial('class', classTemplate);
    Handlebars.registerPartial('method', methodTemplate);
    Handlebars.registerPartial('property', propertyTemplate);

    return Handlebars.compile('{{> class}}', { strict: true, noEscape: true });
  }
}

new Main().run();
