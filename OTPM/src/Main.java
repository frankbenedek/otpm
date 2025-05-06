import java.io.*;
import java.util.List;
import service.*;
import model.*;

public class Main {
    // Fájlok
    private static final String customersFile = "src//customers.csv";
    private static final String ordersFile = "src//orders.csv";
    private static final String output1 = "src//totalByCountry.csv";
    private static final String output2 = "src//totalByMonths.csv";
    private static final String output3 = "src//totalByCountriesAndMonths.csv";


    public static void main(String[] args) {
        // Beolvasás
        List<Customer> customers = CsvReader.readCustomers(customersFile);
        List<Order> orders = CsvReader.readOrders(ordersFile);

        // Statisztikák feladatonként
        List<String[]> totalByCountry = Statistics.getTotalAmountByCountry(customers, orders);
        List<String[]> totalbyMonths = Statistics.getTotalAmountByMonth(orders);
        List<String[]> totalByCountriesAndMonths = Statistics.getTotalAmountByCountryAndMonth(customers, orders);

        // Statisztikák kiíratása CSV-be
        CsvWriter.writeToCsv(output1, totalByCountry, new String[]{"ország","összeg"});
        CsvWriter.writeToCsv(output2, totalbyMonths, new String[]{"hónap", "összeg"});
        CsvWriter.writeToCsv(output3, totalByCountriesAndMonths, new String[]{"ország", "hónap", "összeg"});

    }
}