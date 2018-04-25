import * as Handlebars from 'handlebars';
import * as Typedoc from 'typedoc';

export interface PropertyContext {
  name: string;
  type: string;
}

export interface ArgumentContext {
  name: string;
  type: string;
}

export interface MethodContext {
  name: string;
  arguments: ArgumentContext[];
  returnType: string;
  source: string;
  comment?: string;
}

export interface ClassContext {
  name: string;
  properties: PropertyContext[];
  methods: MethodContext[];
}

export class ClassParser {
  constructor(private sourceLinkPrefix: string) { }

  public parseClasses(classReflections: Typedoc.DeclarationReflection[]): ClassContext[] {
    return classReflections.map((r) => this.parseClass(r));
  }

  private parseClass(classReflection: Typedoc.DeclarationReflection): ClassContext {
    return {
      name: classReflection.name,
      properties: this.parseProperties(classReflection),
      methods: this.parseMethods(classReflection)
    };
  }

  private parseMethods(reflection: Typedoc.DeclarationReflection): MethodContext[] {
    const methodReflections = reflection.getChildrenByKind(Typedoc.ReflectionKind.Method);

    methodReflections.sort((a, b) => a.sources[0].line - b.sources[0].line);

    return methodReflections.map((methodReflection) => ({
      name: methodReflection.name,
      arguments: this.parseArguments(methodReflection.signatures[0].parameters || []),
      returnType: methodReflection.signatures[0].type.toString(),
      source: `${this.sourceLinkPrefix}/${methodReflection.sources[0].fileName}#L${methodReflection.sources[0].line}`,
      comment: methodReflection.signatures[0].comment ? methodReflection.signatures[0].comment.shortText : ''
    }));
  }

  private parseArguments(parameters: Typedoc.ParameterReflection[]): ArgumentContext[] {
    return parameters.map((parameter) => ({
      name: parameter.name,
      type: parameter.type.toString()
    }));
  }

  private parseProperties(reflection: Typedoc.DeclarationReflection): PropertyContext[] {
    const propsReflections = reflection.getChildrenByKind(Typedoc.ReflectionKind.Property);
    return propsReflections.map((propReflection) => ({
      name: propReflection.name,
      type: propReflection.type.toString()
    }));
  }
}
