package service;

import model.Customer;
import model.Order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    // Customer adatokat beolvasása
    public static List<Customer> readCustomers(String filename) {
        List<Customer> customers = new ArrayList<>();
        BufferedReader reader = null;
        String line;

        try {
            // Fájl megnyitása olvasásra
            reader = new BufferedReader(new FileReader(filename));
            reader.readLine(); // fejléc kihagyása
            // Soronként végigolvasás
            while ((line = reader.readLine()) != null) {
                // Vesszők mentén feldarabolás
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String country = parts[2];
                // Customer objektum létrehozása a beolvasottak alapján
                customers.add(new Customer(id, name, country));
            }
        } catch (Exception e) {
            // Hiba esetén log
            e.printStackTrace();
        } finally {
            // Fájl bezárása
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return customers;
    }

    // Order adatok beolvasása
    public static List<Order> readOrders(String filename) {
        List<Order> orders = new ArrayList<>();
        // A dátumok formátuma a fájl alapján
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        BufferedReader reader = null;
        String line;

        try {
            // Fájl megnyitása olvasásra
            reader = new BufferedReader(new FileReader(filename));
            reader.readLine(); // fejléc kihagyása
            // Soronként végigolvasás
            while ((line = reader.readLine()) != null) {
                // Vesszők mentén feldarabolás
                String[] parts = line.split(",");
                int orderId = Integer.parseInt(parts[0]);
                int customerId = Integer.parseInt(parts[1]);
                // A dátum stringből LocalDate objektum
                LocalDate date = LocalDate.parse(parts[2], formatter);
                double amount = Double.parseDouble(parts[3]);
                String currency = parts[4];
                // Order objektum létrehozása a beolvasottak alapján
                orders.add(new Order(orderId, customerId, date, amount, currency));
            }
        } catch (Exception e) {
            // Hiba esetén log
            e.printStackTrace();
        } finally {
            // Fájl bezárása
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return orders;
    }
}
