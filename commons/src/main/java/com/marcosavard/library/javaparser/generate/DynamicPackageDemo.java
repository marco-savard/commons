package com.marcosavard.library.javaparser.generate;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.domain.purchasing.model.PurchaseOrderModel;

public class DynamicPackageDemo {

    public static void main(String[] args) {
        Class[] classes = PurchaseOrderModel.class.getClasses();
        DynamicPackage pack = new DynamicPackage(classes);
        demoTopLevelOwner(pack);

        /*

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

*/








     //   List<List<Class>> concreteClasses = pack.expandConcreteClasses(fieldTypes);
      //  Console.println(signatures);

    }

    private static void demoTopLevelOwner(DynamicPackage pack) {
        for (Class claz : pack.getClasses()) {
            Console.println("{0} has container : {1}", claz.getSimpleName(), pack.hasContainer(claz));
        }
    }


}
