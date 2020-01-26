package com.marcosavard.commons.meta.io;

import static java.util.stream.Collectors.toList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import com.marcosavard.commons.io.FileSystem;
import com.marcosavard.commons.io.IndentWriter;
import com.marcosavard.commons.meta.classes.MetaClass;
import com.marcosavard.commons.meta.classes.MetaConstructor;
import com.marcosavard.commons.meta.classes.MetaDataType;
import com.marcosavard.commons.meta.classes.MetaEnum;
import com.marcosavard.commons.meta.classes.MetaEnumLiteral;
import com.marcosavard.commons.meta.classes.MetaModel;
import com.marcosavard.commons.meta.classes.MetaPackage;
import com.marcosavard.commons.meta.classes.MetaParameter;
import com.marcosavard.commons.meta.classes.MetaReference;
import com.marcosavard.commons.meta.classes.MetaStructuralFeature;
import com.marcosavard.commons.meta.io.PojoWriterOptions.AccessorOrder;

public class PojoWriter {
  private PojoWriterOptions options;

  PojoWriter(PojoWriterOptions options) {
    this.options = options;
  }

  public void write(MetaModel model) {
    int nbFiles = model.getMetaClasses().size() + model.getMetaEnums().size();
    File rootFolder = model.getRootFolder();
    String msg = MessageFormat.format("Generating {0} file(s) in folder {1}", nbFiles, rootFolder);
    options.getProgressMonitor().beginTask(msg, nbFiles);
    List<MetaDataType> types = model.getMetaTypes();

    for (MetaDataType mt : types) {
      generateEntity(mt);
    }

    msg = MessageFormat.format("{0} file(s) generated", nbFiles);
    options.getProgressMonitor().terminateTask(msg);
  }

  private void generateEntity(MetaDataType mt) {
    MetaPackage mp = mt.getPackage();
    MetaModel model = mp.getModel();
    File rootFolder = model.getRootFolder();
    File sourceFolder = new File(rootFolder, options.getSourceFolder());
    String packName = mp.getName();
    File packageFolder = FileSystem.getPackageFolder(sourceFolder, packName);

    String sourceName = mt.getName() + ".java";
    File sourceFile = new File(packageFolder, sourceName);
    int indentation = options.getIndentation();
    int work = 0;

    try {
      IndentWriter iw = new IndentWriter(new FileWriter(sourceFile), indentation);
      buildTypeContent(iw, mt);
      iw.close();

      String subtask = MessageFormat.format("  ..generating {0}.{1}", mp.getName(), sourceName);
      options.getProgressMonitor().worked(subtask, work);
    } catch (IOException ex) {
      sourceName = "?";
    }

  }

  private void buildTypeContent(IndentWriter iw, MetaDataType type) {
    if (type instanceof MetaEnum) {
      buildEnumContent(iw, (MetaEnum) type);
    } else {
      buildClassContent(iw, (MetaClass) type);
    }
  }

  private void buildEnumContent(IndentWriter iw, MetaEnum me) {
    generatePackage(iw, me);
    generateEnumDeclaration(iw, me);
    generateEnumLiterals(iw, me);
    iw.println("}");
  }

  private void buildClassContent(IndentWriter iw, MetaClass mc) {
    generatePackage(iw, mc);
    generateImports(iw, mc);
    generateClassDescription(iw, mc);
    generateClassDeclaration(iw, mc);
    iw.indent();

    generateConstants(iw, mc);
    generateFields(iw, mc);
    generateConstructors(iw, mc);
    generateAccessors(iw, mc);
    generateEquals(iw, mc);
    generateHashCode(iw, mc);
    generateToString(iw, mc);
    generatePrivateMethods(iw, mc);

    iw.unindent();
    iw.println("}");
  }



  //
  // package
  //
  private void generatePackage(IndentWriter iw, MetaDataType me) {
    String packageName = me.getPackage().getName();
    // packageName = packageName.substring(0, packageName.lastIndexOf('.'));
    iw.println(MessageFormat.format("package {0};", packageName));
    iw.println();
  }

  //
  // import
  //
  private void generateImports(IndentWriter iw, MetaClass mc) {
    Set<String> imports = new TreeSet<String>();
    List<MetaStructuralFeature> structuralFeatures = mc.getMetaFeatures();
    boolean hasCollection = false;

    for (MetaStructuralFeature feature : structuralFeatures) {
      hasCollection = hasCollection || feature.isCollection();
    }

    if (hasCollection) {
      imports.add("java.util.List");
      imports.add("java.util.ArrayList");
    }

    imports.add("java.util.Objects");

    /*
     * for (MetaField f : fields) { String typeName = f.getTypeName();
     * 
     * if (typeName.contains(".")) { if (! typeName.startsWith("java.lang")) { if (!
     * typeName.contains("$")) { imports.add(typeName); } } } }
     */

    if (imports.size() > 0) {
      for (String importClause : imports) {
        iw.println(MessageFormat.format("import {0};", importClause));
      }
      iw.println();
    }
  }

  //
  // enum
  //
  private void generateEnumDeclaration(IndentWriter iw, MetaEnum me) {
    String name = me.getName();
    iw.println(MessageFormat.format("public enum {0} '{'", name));
  }

  private void generateEnumLiterals(IndentWriter iw, MetaEnum me) {
    List<MetaEnumLiteral> literals = me.getLiterals();

    for (MetaEnumLiteral l : literals) {
      iw.println(MessageFormat.format("  {0},", l.getName()));
    }
  }

  // javadoc
  private void generateClassDescription(IndentWriter iw, MetaClass mc) {
    String desc = mc.getDescription();

    if (desc != null) {
      iw.println("/**");
      iw.println(" * " + desc);
      iw.println(" */");
    }
  }

  //
  // class
  //
  private void generateClassDeclaration(IndentWriter iw, MetaClass mc) {
    String name = mc.getName();
    MetaClass superClass = mc.getSuperclass();
    String modifiers = mc.getModifierText();

    if (superClass != null) {
      String superClassName = superClass.getName();
      iw.println(
          MessageFormat.format("{0} class {1} extends {2} '{'", modifiers, name, superClassName));
    } else {
      iw.println(MessageFormat.format("{0} class {1} '{'", modifiers, name));
    }
  }

  //
  // constants
  //
  private void generateConstants(IndentWriter iw, MetaClass mc) {
    List<MetaStructuralFeature> features = mc.getMetaFeatures();
    List<MetaStructuralFeature> constants =
        features.stream().filter(f -> f.isConstant()).collect(Collectors.toList());

    if (constants.size() > 0) {
      for (MetaStructuralFeature ct : constants) {
        String visibility = ct.getVisibilityModifier();
        String typename = ct.getTypeName();
        String name = ct.getName();
        String value = ct.getValue();
        iw.println(MessageFormat.format("{0} static final {1} {2} = {3};", visibility, typename,
            name, value));
      }

      iw.println();
    }

    if (options.writeGenericAccessor) {
      List<MetaStructuralFeature> variables =
          features.stream().filter(f -> !f.isConstant()).collect(Collectors.toList());

      iw.println("private static final String[] META_FIELDS = new String[] {");
      iw.indent();
      int nb = variables.size();

      for (int i = 0; i < nb; i++) {
        MetaStructuralFeature variable = variables.get(i);
        boolean last = (i == nb - 1);
        iw.print("\"" + variable.getName() + "\"");
        iw.println(last ? "" : ",");
      }

      iw.unindent();
      iw.println("};");
      iw.println();
    }
  }

  //
  // variables
  //
  private void generateFields(IndentWriter iw, MetaClass mc) {
    List<MetaStructuralFeature> features = mc.getMetaFeatures();
    List<MetaStructuralFeature> variables =
        features.stream().filter(f -> !f.isConstant()).collect(Collectors.toList());

    if (variables.size() > 0) {
      for (MetaStructuralFeature f : variables) {
        String modifiers = getModifiers(mc, f);
        String typename = f.getTypeName();
        String name = f.getName();

        if (f.isCollection()) {
          iw.println(MessageFormat.format("{0} List<{1}> {2} = new ArrayList<>();", modifiers,
              typename, name));
        } else {
          iw.println(MessageFormat.format("{0} {1} {2};", modifiers, typename, name));
        }
      }

      iw.println();
    }

    /*
     * 
     * List<MetaField> variables = fields.stream().filter(f -> !
     * f.isConstant()).collect(Collectors.toList());
     * 
     * if (variables.size() > 0) { for (MetaField f : variables) { List<String> modifierList = new
     * ArrayList<>(); modifierList.add("private"); boolean readonly = f.isReadOnly();
     * 
     * if (readonly) { modifierList.add("final"); }
     * 
     * String modifiers = String.join(" ", modifierList); String typename = f.getFieldTypeText();
     * String name = f.getName(); iw.println(MessageFormat.format("{0} {1} {2};", modifiers,
     * typename, name)); }
     * 
     * iw.println(); }
     */
  }

  private String getModifiers(MetaClass mc, MetaStructuralFeature f) {
    List<String> modifiers = new ArrayList<>();
    modifiers.add("private");
    boolean required = mc.isImmutable() || (f.isRequired() && f.isReadOnly());

    if (f.isFinal()) {
      modifiers.add("final");
    } else if (required) {
      modifiers.add("final");
    }

    return String.join(" ", modifiers);
  }

  //
  // constructors
  //
  private void generateConstructors(IndentWriter iw, MetaClass mc) {
    List<MetaConstructor> constructors = mc.getMetaConstructors();

    for (MetaConstructor constr : constructors) {
      generateConstructor(iw, mc, constr);
    }
  }

  private void generateConstructor(IndentWriter iw, MetaClass mc, MetaConstructor constr) {
    List<MetaParameter> parameters = constr.getMetaParameters();
    List<String> parameterList = new ArrayList<>();

    iw.println("/**");
    for (MetaParameter mp : parameters) {
      iw.println(" * @param " + mp.getName());
    }
    iw.println(" */");

    String visibility = mc.isAbstract() ? "protected" : "public";
    String name = mc.getName();
    iw.print(MessageFormat.format("{0} {1}(", visibility, name));

    for (MetaParameter mp : parameters) {
      String typeName = mp.getType().getName();
      String fieldname = mp.getName();
      parameterList.add(typeName + " " + fieldname);
    }

    iw.print(String.join(", ", parameterList));
    iw.println(") {");
    iw.indent();

    generateConstructorBodyForParameters(iw, mc, constr);

    iw.unindent();
    iw.println("}");
    iw.println();
  }

  private void generateConstructorBodyForParameters(IndentWriter iw, MetaClass mc,
      MetaConstructor constr) {
    List<MetaParameter> parameters = constr.getMetaParameters();
    MetaClass smc = mc.getSuperclass();
    MetaConstructor superConstr = null;
    boolean callSuper = false;

    MetaConstructor constrWithMoreArgs =
        findConstructorWithMoreArgs(constr, mc.getMetaConstructors());

    if (constrWithMoreArgs != null) {
      // generate this(param, ..);
      generateCallToThisConstructor(iw, constr, constrWithMoreArgs);
    } else {
      if (smc != null) {
        // generate super(param, ..);
        List<MetaConstructor> superConstrs = smc.getMetaConstructors();
        superConstr = findSuperConstructor(superConstrs, parameters);
        callSuper = (superConstr != null);
      }

      if (callSuper) {
        generateCallToSuper(iw, superConstr);
      }

      for (MetaParameter mp : parameters) {
        generateConstructorFieldSetter(iw, mc, mp);
      }

      for (MetaParameter mp : parameters) {
        boolean required = mc.isImmutable() || (mp.isRequired());
        if (required) {
          generateCheckRequiredParameter(iw, mp);
        }
      }
    }
  }

  private void generateCheckRequiredParameter(IndentWriter iw, MetaParameter mp) {
    String typeName = mp.getTypeName();

    if (!isPrimitive(typeName)) {
      iw.println();
      iw.println(MessageFormat.format("if ({0} == null) '{'", mp.getName()));
      iw.println(MessageFormat.format(
          "  throw new NullPointerException(\"Parameter ''{0}'' is required\");", mp.getName()));
      iw.println("}");
    }
  }

  private void generateCallToThisConstructor(IndentWriter iw, MetaConstructor constr,
      MetaConstructor constrWithMoreArgs) {
    List<MetaParameter> callerParams = constr.getMetaParameters();
    List<MetaParameter> calleeParams = constrWithMoreArgs.getMetaParameters();
    int nb = calleeParams.size();
    iw.print("this(");

    for (int i = 0; i < nb; i++) {
      MetaParameter mp = calleeParams.get(i);
      boolean present = callerParams.stream().filter(p -> p.getName().equals(mp.getName()))
          .findFirst().isPresent();

      if (present) {
        iw.print(mp.getName());
      } else {
        String defValue = getDefaultValue(mp.getType().getName());
        iw.print(defValue);
      }

      if (i < nb - 1) {
        iw.print(", ");
      }
    }

    iw.println(");");
  }

  private String getDefaultValue(String typeName) {
    String defaultValue = "(" + typeName + ")null";

    if ("boolean".equals(typeName)) {
      defaultValue = "false";
    } else if ("double".equals(typeName)) {
      defaultValue = "0.0";
    } else if ("int".equals(typeName)) {
      defaultValue = "0";
    } else if ("long".equals(typeName)) {
      defaultValue = "0L";
    }

    return defaultValue;
  }

  private MetaConstructor findConstructorWithMoreArgs(MetaConstructor constr,
      List<MetaConstructor> otherConstructors) {
    MetaConstructor constructorWithMoreArgs = null;

    for (MetaConstructor otherConstructor : otherConstructors) {
      if (otherConstructor.getNbParameters() > constr.getNbParameters()) {
        constructorWithMoreArgs = otherConstructor;
        break;
      }
    }

    return constructorWithMoreArgs;
  }

  private MetaConstructor findSuperConstructor(List<MetaConstructor> superConstrs,
      List<MetaParameter> parameters) {
    List<MetaConstructor> candidates = new ArrayList<>();;

    for (MetaConstructor sc : superConstrs) {
      List<MetaParameter> scps = sc.getMetaParameters();
      boolean allPresent = true;

      for (MetaParameter scp : scps) {
        String name = scp.getName();
        boolean present =
            parameters.stream().filter(p -> p.getName().equals(name)).findFirst().isPresent();
        allPresent = allPresent && present;
      }

      if (allPresent) {
        candidates.add(sc);
      }
    }

    candidates.sort(Comparator.comparing(MetaConstructor::getNbParameters));
    Collections.reverse(candidates);
    MetaConstructor foundContr = (candidates.size() > 0) ? candidates.get(0) : null;
    return foundContr;
  }

  private void generateCallToSuper(IndentWriter iw, MetaConstructor constr) {
    iw.print("super(");
    List<MetaParameter> params = constr.getMetaParameters();

    for (int i = 0; i < params.size(); i++) {
      MetaParameter param = params.get(i);
      iw.print(param.getName());
      if (i < params.size() - 1) {
        iw.print(", ");
      }
    }
    iw.println(");");
  }

  private void generateConstructorFieldSetter(IndentWriter iw, MetaClass mc, MetaParameter mp) {
    MetaStructuralFeature feature = mc.getMetaFeature(mp.getName());

    if (feature != null) {
      boolean required = mc.isImmutable() || mp.isRequired();

      if (required) {
        iw.println(MessageFormat.format("this.{0} = {0};", mp.getName()));
      } else {
        String capitalized = capitalize(mp.getName());
        iw.println(MessageFormat.format("set{0}({1});", capitalized, mp.getName()));
      }
    }
  }

  private void generateAccessors(IndentWriter iw, MetaClass mc) {
    if (options.getAccessorOrder() == AccessorOrder.GROUP_BY_ACCESSOR_TYPE) {
      generateGetters(iw, mc);
      generateSetters(iw, mc);
    } else if (options.getAccessorOrder() == AccessorOrder.GROUP_BY_PROPERTY) {
      List<MetaStructuralFeature> features = mc.getMetaFeatures();

      for (MetaStructuralFeature f : features) {
        if (!f.isConstant()) {
          generateGetter(iw, f);
          generateSetter(iw, f);
        }
      }
    }

    if (options.writeGenericAccessor) {
      generateGetMetaFields(iw);
      generateGenericGetter(iw, mc);
      generateGenericSetter(iw, mc);
    }
  }

  private void generateGetMetaFields(IndentWriter iw) {
    iw.println("public static String[] getMetaFields() {");
    iw.println("  return META_FIELDS;");
    iw.println("}");
    iw.println();
  }

  private void generateGetters(IndentWriter iw, MetaClass mc) {
    List<MetaStructuralFeature> features = mc.getMetaFeatures();

    for (MetaStructuralFeature f : features) {
      if (!f.isConstant()) {
        generateGetter(iw, f);
      }
    }
  }

  private void generateSetters(IndentWriter iw, MetaClass mc) {
    List<MetaStructuralFeature> allFeatures = mc.getMetaFeatures();
    List<MetaStructuralFeature> writableFeatures =
        allFeatures.stream().filter(f -> !f.isFinal()).collect(toList());

    for (MetaStructuralFeature f : writableFeatures) {
      if (!f.isReadOnly() && !f.isConstant()) {
        generateSetter(iw, f);
      }
    }
  }

  private void generateGetter(IndentWriter iw, MetaStructuralFeature f) {
    String visibility = f.getVisibilityModifier();
    String getterName = f.getGetterName();
    String typename = f.getTypeName();

    iw.println("/**");
    iw.println(" * @return " + f.getDescription());
    iw.println(" */");

    if (f.isCollection()) {
      iw.println(MessageFormat.format("{0} List<{1}> {2}() '{'", visibility, typename, getterName));
    } else {
      iw.println(MessageFormat.format("{0} {1} {2}() '{'", visibility, typename, getterName));
    }

    iw.indent();
    iw.println(MessageFormat.format("return {0};", f.getName()));
    iw.unindent();
    iw.println("}");
    iw.println();
  }

  private void generateSetter(IndentWriter iw, MetaStructuralFeature f) {
    MetaClass owner = f.getOwner();
    boolean writable = !owner.isImmutable();
    writable &= !f.isReadOnly();
    writable &= !f.isConstant();

    if (writable) {
      if (f.isCollection()) {
        generateCollectionSetters(iw, f);
      } else {
        iw.println("/**");
        iw.println(" * @param " + f.getDescription());
        iw.println(" */");

        String visibility = f.getVisibilityModifier();
        String typename = f.getTypeName();
        String name = capitalize(f.getName());
        iw.println(MessageFormat.format("{0} void set{1}({2} {3}) '{'", visibility, name, typename,
            f.getName()));
        iw.indent();

        if (f.isRequired()) {
          if (!isPrimitive(typename)) {
            iw.println(MessageFormat.format("if ({0} == null) '{'", f.getName()));
            iw.println(MessageFormat.format(
                "  throw new NullPointerException(\"Parameter ''{0}'' is required\");",
                f.getName()));
            iw.println("}");
            iw.println();
          }
        }

        iw.println(MessageFormat.format("this.{0} = {0};", f.getName()));
        iw.unindent();
        iw.println("}");
        iw.println();
      }
    }
  }

  private void generateCollectionSetters(IndentWriter iw, MetaStructuralFeature f) {

    if (f instanceof MetaReference) {
      MetaReference mr = (MetaReference) f;

      if (mr.isComposition()) {
        generateFactory(iw, f);
      } else {
        generateAdder(iw, f);
      }

      generateRemover(iw, f);
    } else {
      generateAdder(iw, f);
      generateRemover(iw, f);
    }
  }

  private void generateAdder(IndentWriter iw, MetaStructuralFeature f) {
    String visibility = f.getVisibilityModifier();
    String typename = f.getTypeName();
    String name = capitalize(f.getName());
    String parameter = uncapitalize(typename);

    iw.println(MessageFormat.format("{0} void addTo{1}({2} {3}) '{'", visibility, name, typename,
        parameter));
    iw.indent();
    iw.println(MessageFormat.format("this.{0}.add({1});", f.getName(), parameter));
    iw.unindent();
    iw.println("}");
    iw.println();
  }

  private void generateRemover(IndentWriter iw, MetaStructuralFeature f) {
    String visibility = f.getVisibilityModifier();
    String typename = f.getTypeName();
    String name = capitalize(f.getName());
    String parameter = uncapitalize(typename);

    iw.println(MessageFormat.format("{0} void removeFrom{1}({2} {3}) '{'", visibility, name,
        typename, parameter));
    iw.indent();
    iw.println(MessageFormat.format("  this.{0}.remove({1});", f.getName(), parameter));
    iw.unindent();
    iw.println("}");
    iw.println();
  }

  private void generateFactory(IndentWriter iw, MetaStructuralFeature f) {
    String visibility = f.getVisibilityModifier();
    String typename = f.getTypeName();
    String name = capitalize(f.getName());

    iw.println(MessageFormat.format("{0} {1} create{1}In{2}() '{'", visibility, typename, name));
    iw.indent();
    iw.println(MessageFormat.format("  {0} instance = new {0}();", typename));
    iw.println(MessageFormat.format("  {0}.add(instance);", f.getName()));
    iw.println(MessageFormat.format("  return instance;", f.getName()));
    iw.unindent();
    iw.println("}");
    iw.println();
  }

  private void generateGenericGetter(IndentWriter iw, MetaClass mc) {
    // TODO Auto-generated method stub

  }

  private void generateGenericSetter(IndentWriter iw, MetaClass mc) {
    // TODO Auto-generated method stub

  }

  //
  // equals
  //
  private void generateEquals(IndentWriter iw, MetaClass mc) {
    String classname = mc.getName();
    iw.println("@Override");
    iw.println("public boolean equals(Object other) {");
    iw.indent();
    iw.println("boolean equal = false;");
    iw.println();

    iw.println(MessageFormat.format("if (other instanceof {0}) '{'", classname));
    iw.println(MessageFormat.format("  {0} that = ({0})other;", classname));
    iw.println("  equal = (hashCode() == that.hashCode()) ? isEqualTo(that) : false;");
    iw.println("}");
    iw.println();

    iw.println("return equal;");
    iw.unindent();
    iw.println("}");
    iw.println();
  }

  private void generateAreEqual(IndentWriter iw, MetaClass mc) {
    List<MetaStructuralFeature> features = mc.getMetaFeatures();
    List<MetaStructuralFeature> variables =
        features.stream().filter(f -> !f.isConstant()).collect(toList());
    String classname = mc.getName();

    iw.println(MessageFormat.format("private boolean isEqualTo({0} that{0}) '{'", classname));
    iw.indent();
    iw.println("boolean equal = true;");

    for (MetaStructuralFeature f : variables) {
      String typename = f.getTypeName();
      String verb = "boolean".equals(typename) ? "is" : "get";
      String getter = verb + capitalize(f.getName());

      if (isPrimitive(typename)) {
        iw.println(
            MessageFormat.format("equal = equal && ({0}() == that{1}.{0}());", getter, classname));
      } else {
        iw.println(MessageFormat.format(
            "equal = equal && ({0}() == null ? that{1}.{0}() == null : {0}().equals(that{1}.{0}()));",
            getter, classname));
      }

    }

    iw.println("return equal;");
    iw.unindent();
    iw.println("}");
    iw.println();

  }

  private boolean isPrimitive(String typename) {
    boolean primitive = false;
    primitive = primitive || "boolean".equals(typename);
    primitive = primitive || "byte".equals(typename);
    primitive = primitive || "double".equals(typename);
    primitive = primitive || "float".equals(typename);
    primitive = primitive || "int".equals(typename);
    primitive = primitive || "long".equals(typename);
    primitive = primitive || "short".equals(typename);

    return primitive;
  }

  //
  // hashCode()
  //
  private void generateHashCode(IndentWriter iw, MetaClass mc) {
    List<String> fieldList = getGetterList(mc);
    String fields = String.join(", ", fieldList);

    iw.println("@Override");
    iw.println("public int hashCode() {");
    iw.indent();
    iw.println(MessageFormat.format("int hashCode = Objects.hash({0});", fields));
    iw.println("return hashCode;");
    iw.unindent();
    iw.println("}");
    iw.println();
  }

  //
  // toString
  //
  private void generateToString(IndentWriter iw, MetaClass mc) {
    List<MetaStructuralFeature> features = mc.getMetaFeatures();
    List<MetaStructuralFeature> variables =
        features.stream().filter(f -> !f.isConstant()).collect(toList());

    iw.println("@Override");
    iw.println("public String toString() {");
    iw.indent();
    iw.println("StringBuilder sb = new StringBuilder();");
    iw.println("sb.append(\"{\");");

    for (int i = 0; i < variables.size(); i++) {
      MetaStructuralFeature f = variables.get(i);
      boolean last = i == (variables.size() - 1);
      String separator = last ? "" : ", ";
      String getter = f.getGetterName() + "String()";
      iw.println(MessageFormat.format("sb.append(\"{0} : \" + {1} + \"{2}\");", f.getName(), getter,
          separator));
    }

    iw.println("sb.append(\"}\");");
    iw.println("return sb.toString();");
    iw.unindent();
    iw.println("}");
    iw.println();
  }

  private void generatePrivateMethods(IndentWriter iw, MetaClass mc) {
    List<MetaStructuralFeature> features = mc.getMetaFeatures();
    List<MetaStructuralFeature> variables =
        features.stream().filter(f -> !f.isConstant()).collect(toList());
    generateAreEqual(iw, mc);

    for (MetaStructuralFeature f : variables) {
      generateFieldToString(iw, mc, f);
    }
  }

  private void generateFieldToString(IndentWriter iw, MetaClass mc, MetaStructuralFeature f) {
    String getter = f.getGetterName();
    iw.println(MessageFormat.format("private String {0}String() '{'", getter));
    iw.indent();
    iw.println(MessageFormat.format("Object value = {0}();", f.getGetterName()));
    iw.println("String s;");
    iw.println();
    iw.println("if (value instanceof String) {");
    iw.println("  s = \"\\\"\" + value + \"\\\"\";");
    iw.println("} else {");
    iw.println("  s = (value == null) ? null : value.toString();");
    iw.println("}");
    iw.println();
    iw.println("return s;");
    iw.unindent();
    iw.println("}");
    iw.println();
  }

  private List<String> getGetterList(MetaClass mc) {
    List<String> fieldList = new ArrayList<>();
    List<MetaStructuralFeature> fields = mc.getMetaFeatures();

    for (MetaStructuralFeature f : fields) {
      if (!f.isConstant()) {
        String getter = f.getGetterName() + "()";
        fieldList.add(getter);
      }
    }

    return fieldList;
  }


  private String capitalize(String name) {
    String capitalized = Character.toUpperCase(name.charAt(0)) + name.substring(1);
    return capitalized;
  }

  private String uncapitalize(String name) {
    String capitalized = Character.toLowerCase(name.charAt(0)) + name.substring(1);
    return capitalized;
  }



}
