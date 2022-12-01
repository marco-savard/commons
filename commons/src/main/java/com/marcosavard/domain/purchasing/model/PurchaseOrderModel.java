package com.marcosavard.domain.purchasing.model;

import com.marcosavard.commons.lang.reflect.meta.annotations.Component;
import com.marcosavard.commons.lang.reflect.meta.annotations.Description;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PurchaseOrderModel {

    public static class Supplier {
        public static int supplierCount;
        public final String name = "Unknown";
        public @Component List<Customer> customers;
        public @Component List<PurchaseOrder> orders;
    }

    public static class Customer {
        public final long customerId = 1;
        public List<PurchaseOrder> orders;
    }

    public static class PurchaseOrder {
        public Customer customer;
        public @Component List<Item> items;
        public @Component Address billTo;
        public @Component Optional<Address> shipTo;
        public LocalDate orderDate;
        public OrderStatus status = OrderStatus.PENDING;
        public Optional<String> comment;
        public Optional<PurchaseOrder> previousOrder;
    }

    public static class Item {
        public final String sku = "?";
        public final int quantity = 1;
        public String productName;
        public LocalDate shipDate;
    }

    @Description("represents a generic address")
    public abstract static class Address {
        public final String civicNumber = "?";
        public String street;
        public String city;
    }

    public static class USAddress extends Address {
        @Description("two-letter state code")
        public String stateCode;
        public final String zipCode = "?";
    }

    public static class GlobalAddress extends Address {
        public final String postalCode = "?";
        public String country = "?";
    }

    public enum OrderStatus {
        PENDING, BACK_ORDER, COMPLETE
    }


}
