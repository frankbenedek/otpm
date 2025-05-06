package model;

import java.time.LocalDate;
public class Order {
    private int orderId;
    private int customerId;
    private LocalDate orderDate;
    private double totalAmount;
    private String currency;

    // Konstruktor
    public Order(int orderId, int customerId, LocalDate orderDate, double totalAmount, String currency) {
    this.orderId = orderId;
    this.customerId = customerId;
    this.orderDate = orderDate;
    this.totalAmount = totalAmount;
    this.currency = currency;
    }

    // Getterek
    public int getOrderId() {
        return orderId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public String getCurrency() {
        return currency;
    }
}
