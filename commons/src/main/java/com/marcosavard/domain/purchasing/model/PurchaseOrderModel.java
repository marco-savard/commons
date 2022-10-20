package com.marcosavard.domain.purchasing.model;

import com.marcosavard.commons.meta.annotations.Component;
import com.marcosavard.commons.meta.annotations.Description;
import com.marcosavard.commons.meta.annotations.NotNull;
import com.marcosavard.commons.meta.annotations.Readonly;
import com.marcosavard.domain.purchasing.PurchaseOrder;

import java.time.LocalDate;
import java.util.List;

public class PurchaseOrderModel {

    public class Supplier {
        public @Readonly String name;
        public @Component List<Customer> customers;
        public @Component List<PurchaseOrder> orders;
    }

    public class Customer {
        public @Readonly long customerId;
        public List<PurchaseOrder> orders;
    }

    public static class PurchaseOrder {
        public LocalDate orderDate;
        public OrderStatus status;
        public String comment;
        public PurchaseOrder previousOrder;
        public @Component List<Item> items;
        public @Component @NotNull Address billTo;
        public @Component Address shipTo;
    }

    public static class Item {
        public @Readonly String sku;
        public @Readonly int quantity;
        public String productName;
        public LocalDate shipDate;
    }





    @Description("represents a generic address")
    public abstract static class Address {
        public String name;
        public String country;
    }

    public static class USAddress extends Address {
        public String street;
        public String city;
        @Description("two-letter state code")
        public String stateCode;
        public String zipCode;
    }

    public enum OrderStatus {
        PENDING, BACK_ORDER, COMPLETE
    }


}
