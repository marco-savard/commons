package com.marcosavard.library.javaparser.generate;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.lang.reflect.meta.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;

public class SourceBasedPojoGenerator extends PojoGenerator {
    protected CompilationUnit cu;

    public SourceBasedPojoGenerator(File outputFolder, Reader reader) {
        super(outputFolder);
        JavaParser parser = new JavaParser();
        this.cu = parser.parse(reader);
    }

    public SourceBasedPojoGenerator(File outputFolder, CompilationUnit cu) {
        super(outputFolder);
        this.cu = cu;
    }

    //entry point
    @Override
    public List<File> generate() throws IOException {
        List<MetaClass> metaClasses = generate(this.cu);
        List<File> files = new ArrayList<>();

        for (MetaClass mc : metaClasses) {
            files.add(super.generateFile(mc));
        }

        return files;
    }


    public List<MetaClass> generate(CompilationUnit cu) throws IOException {
        //pass 1 : generate meta classes
        PackageDeclaration pack = cu.getPackageDeclaration().orElse(null);
        SourceMetaPackage mp = new SourceMetaPackage(cu, pack);
        NodeList<TypeDeclaration<?>> types = cu.getTypes();
        List<MetaClass> metaClasses = new ArrayList<>();
        List<File> generatedFiles = new ArrayList<>();

        for (TypeDeclaration type : types) {
            List<Node> nodes = type.getChildNodes();

            for (Node node : nodes) {
                if (node instanceof TypeDeclaration<?>) {
                    TypeDeclaration<?> td = (TypeDeclaration<?>)node;
                    MetaClass mc = new SourceMetaClass(mp, cu, td);
                    metaClasses.add(mc);
                }
            }
        }

        //pass 2 : find references
        for (MetaClass mc : metaClasses) {
            List<MetaField> fields = mc.getAllVariables();
            for (MetaField mf : fields) {
                if (mf.isComponent()) {
                    MetaClass type = mf.getType();
                    MetaClass child = type.isCollection() ? mf.getItemType() : type;
                    child = mf.isOptional() ? mf.getItemType() : child;
                    MetaReference mr = MetaReference.of(child, mf, containerName);

                    Console.println("{0}", child.getName());
                }
            }
        }

        //pass 3 : fill meta classes
        for (MetaClass mc : metaClasses) {
            File generated = generateFile(mc);
            generatedFiles.add(generated);
        }

        return metaClasses;
    }

    @Override
    protected String getInitialValue(MetaField mf) {
        return "null";
    }

    @Override
    protected MetaField getReferenceForClass(MetaClass mc) {
        MetaPackage mp = mc.getPackage();
        List<MetaClass> metaClasses = mp.getClasses();
        MetaField opposite = null, reference = null;

        for (MetaField thisField : mc.getDeclaredFields()) {
            if (thisField.getName().equals(containerName)) {
                reference = thisField;
                break;
            }
        }

        if (reference == null) {
            for (MetaClass that : metaClasses) {
                MetaField[] thoseFields = that.getFields();
                for (MetaField thatField : thoseFields) {
                    if (thatField.isComponent()) {
                        MetaClass type = thatField.getType();
                        type = type.isCollection() ? thatField.getItemType() : type;

                        if (type.equals(mc)) {
                            opposite = thatField;
                            break;
                        }
                    }
                }

                if (opposite != null) {
                    break;
                }
            }

            if (opposite != null) {
                reference = MetaReference.of(mc, opposite, containerName);
            }
        }

        return reference;
    }

    @Override
    protected String getGetterName(MetaField mf) {
        String verb = "get";
        return verb + StringUtil.capitalize(mf.getName());
    }

    @Override
    protected List<MetaClass> getSubClasses(MetaClass givenClass) {
        MetaPackage mp = givenClass.getPackage();
        List<MetaClass> classes = mp.getClasses();
        List<MetaClass> subclasses = new ArrayList<>();

        for (MetaClass mc : classes) {
            if (givenClass.equals(mc.getSuperClass())) {
                subclasses.add(mc);
            }
        }

        return subclasses;
    }



}
