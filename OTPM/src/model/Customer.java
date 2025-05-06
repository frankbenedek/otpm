package model;

public class Customer {
    private int customerId;
    private String name;
    private String country;

    // Konstruktor
    public Customer(int customerId, String name, String country) {
        this.customerId = customerId;
        this.name = name;
        this.country = country;
    }

    // Getterek
    public int getCustomerId() {
        return customerId;
    }
    public String getName() {
        return name;
    }
    public String getCountry() {
        return country;
    }
}
