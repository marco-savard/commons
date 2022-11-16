package com.marcosavard.library.javaparser.generate;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.marcosavard.commons.lang.StringUtil;
import com.marcosavard.commons.lang.reflect.meta.MetaClass;
import com.marcosavard.commons.lang.reflect.meta.MetaField;
import com.marcosavard.commons.lang.reflect.meta.MetaPackage;
import com.marcosavard.commons.lang.reflect.meta.PojoGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SourceBasedPojoGenerator extends PojoGenerator {
    protected CompilationUnit cu;

    protected SourceBasedPojoGenerator(File outputFolder, CompilationUnit cu) {
        super(outputFolder);
        this.cu = cu;
    }

    //entry point
    public List<File> generate(CompilationUnit cu) throws IOException {
        //pass 1 : generate meta classes
        PackageDeclaration pack = cu.getPackageDeclaration().orElse(null);
        SourceMetaPackage mp = new SourceMetaPackage(cu, pack);
        NodeList<TypeDeclaration<?>> types = cu.getTypes();
        List<MetaClass> metaClasses = new ArrayList<>();
        List<File> generatedFiles = new ArrayList<>();

        for (TypeDeclaration type : types) {
            List<Node> nodes = type.getChildNodes();

            for (Node node : nodes) {
                if (node instanceof TypeDeclaration<?> td) {
                    MetaClass mc = new SourceMetaClass(mp, td);
                    metaClasses.add(mc);
                }
            }
        }

        //pass 2 : fill meta classes
        for (MetaClass mc : metaClasses) {
            File generated = generateClass(mc);
            generatedFiles.add(generated);
        }

        return generatedFiles;
    }


    @Override
    protected String getInitialValue(MetaField mf) {
        return "null";
    }

    @Override
    protected MetaField getReferenceForClass(MetaClass mc) {
        return null;
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
                //TODO subclasses.add(mc);
            }
        }

        return subclasses;
    }


}
