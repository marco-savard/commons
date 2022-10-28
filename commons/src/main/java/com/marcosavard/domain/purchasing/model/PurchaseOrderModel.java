package com.marcosavard.domain.purchasing.model;

import com.marcosavard.commons.meta.annotations.Component;
import com.marcosavard.commons.meta.annotations.Description;
import com.marcosavard.commons.meta.annotations.NotNull;
import com.marcosavard.commons.meta.annotations.Readonly;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        public @NotNull Customer customer;
        public @Component List<Item> items;
        public @Component @NotNull Address billTo;
        public @Component Address shipTo;
        public LocalDate orderDate;
        public OrderStatus status;
        public Optional<String> comment;
        public Optional<PurchaseOrder> previousOrder;
    }

    public static class Item {
        public @Readonly String sku;
        public @Readonly int quantity;
        public String productName;
        public LocalDate shipDate;
    }

    @Description("represents a generic address")
    public abstract static class Address {
        public @Readonly String civicNumber;
        public String street;
        public String city;
    }

    public static class USAddress extends Address {
        @Description("two-letter state code")
        public String stateCode;
        public @Readonly String zipCode;
    }

    public static class GlobalAddress extends Address {
        public @Readonly String postalCode;
        public String country;
    }

    public enum OrderStatus {
        PENDING, BACK_ORDER, COMPLETE
    }


}
