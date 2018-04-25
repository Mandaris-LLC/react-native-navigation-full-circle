import * as Handlebars from 'handlebars';
import * as fs from 'fs';
import { ClassContext } from './ClassParser';

export class MarkdownWriter {
  private classFn;
  private menuFn;
  constructor(private templatesDir: string, private outputDir: string) {
    this.classFn = this.setupClassHandlebars();
    this.menuFn = this.setupMenuHandlebars();
  }

  public writeClasses(classContexts: ClassContext[]) {
    classContexts.forEach((c) => {
      const classMarkdown = this.classFn(c);
      fs.writeFileSync(`${this.outputDir}/${c.name}.md`, classMarkdown, { encoding: 'utf8' });
    });
  }

  public writeMenu(classContexts: ClassContext[]) {
    const files = classContexts.map((c) => ({ name: c.name, path: `/api/${c.name}` }));
    const menuMarkdown = this.menuFn({ files });
    fs.writeFileSync(`${this.outputDir}/_sidebar.md`, menuMarkdown, { encoding: 'utf8' });
    fs.writeFileSync(`${this.outputDir}/README.md`, menuMarkdown, { encoding: 'utf8' });
  }

  private setupClassHandlebars() {
    const classTemplate = fs.readFileSync(`${this.templatesDir}/class.hbs`).toString();
    const methodTemplate = fs.readFileSync(`${this.templatesDir}/method.hbs`).toString();
    const propertyTemplate = fs.readFileSync(`${this.templatesDir}/property.hbs`).toString();

    Handlebars.registerPartial('class', classTemplate);
    Handlebars.registerPartial('method', methodTemplate);
    Handlebars.registerPartial('property', propertyTemplate);

    return Handlebars.compile('{{> class}}', { strict: true, noEscape: true });
  }

  private setupMenuHandlebars() {
    const template = fs.readFileSync(`${this.templatesDir}/menu.hbs`).toString();
    return Handlebars.compile(template, { strict: true, noEscape: true });
  }
}
