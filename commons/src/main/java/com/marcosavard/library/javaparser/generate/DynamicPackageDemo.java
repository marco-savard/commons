package com.marcosavard.library.javaparser.generate;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.domain.purchasing.model.PurchaseOrderModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DynamicPackageDemo {

    public static void main(String[] args) {
        Class[] classes = PurchaseOrderModel.class.getClasses();
        DynamicPackage pack = new DynamicPackage(classes);

        List<Class> fieldTypes = Arrays.asList(new Class[] {
                PurchaseOrderModel.Address.class,
                PurchaseOrderModel.Supplier.class,
                PurchaseOrderModel.Address.class,
                PurchaseOrderModel.Customer.class,
                PurchaseOrderModel.Address.class,

        });
        List<List<Class>> signatures = pack.findConcreteSignatures(fieldTypes);

        for (List<Class> signature : signatures) {
            Console.println(signature);
        }










     //   List<List<Class>> concreteClasses = pack.expandConcreteClasses(fieldTypes);
      //  Console.println(signatures);

    }


}
