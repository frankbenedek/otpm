package service;

import model.Customer;
import model.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Statistics {

    /*
     * MEGJEGYZÉS A FELADATKIÍRÁSSAL KAPCSOLATBAN:
     *
     * A 2. és 3. feladatnál nem volt egyértelmű, hogyan kell kezelni a különböző pénznemű rendeléseket,
     * ugyanis az orders.csv fájlban az összegek eltérő valutákban szerepelnek (pl. HUF, USD, EUR),
     * de a feladat nem adott útmutatást árfolyamra vagy átváltásra vonatkozóan.
     *
     * Mivel nem volt meghatározva, hogy a különböző pénznemeket külön kellene kezelni vagy konvertálni,
     * minden rendelési összeget összegeztem a kategóriák szerint, függetlenül attól, hogy milyen valutában szerepeltek.
     *
     * Ez technikailag helytelen lehet egy valódi pénzügyi kimutatásban, de a feladat leírása alapján ez tűnt
     * a legkövetkezetesebb és kivitelezhető megközelítésnek.
     */


    public static List<String[]> getTotalAmountByCountry(List<Customer> customers, List<Order> orders){
        // Két lista
        List<String> countries = new ArrayList<>();
        List<Double> totals = new ArrayList<>();
        // Végigmegyünk a customer listán
        for (Customer customer : customers) {
            String country = customer.getCountry();
            // Ha az ország még nem szerepel a countries listában akkor hozzáadjuk, az alapértelmezett összeggel (0.0) együtt
            if (!countries.contains(country)) {
                countries.add(country);
                totals.add(0.0);
            }
            // Az eddigi összeg megszerzése az index alapján
            int index = countries.indexOf(country);
            double currentTotal = totals.get(index);

            // Nested for loop hogy végigmenjünk az összes orderen és összehasonlítsuk a customerekkel
            for (Order order : orders) {
                // Ha megegyezik a customerId hozzáadjuk a megfelelő indexhez az összeget
                if (order.getCustomerId() == customer.getCustomerId()) {
                    totals.set(index, currentTotal + order.getTotalAmount());
                    currentTotal = totals.get(index);
                }
            }
        }
        // Egy lista a kettőből
        List <String[]> result = new ArrayList<>();
        for (int i = 0; i < countries.size(); i++) {
            result.add(new String[]{countries.get(i), String.format("%.2f", totals.get(i))});
        }
        // A lista rendezése
        result.sort(Comparator.comparing(a -> a[0]));

        return result;

    }
    public static List<String[]> getTotalAmountByMonth(List<Order> orders) {
        // Formátum beállítása ("2025-04")
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        // Két lista – hónapok és a hozzájuk tartozó összegek
        List<String> months = new ArrayList<>();
        List<Double> totals = new ArrayList<>();

        // Végigmegyünk az összes rendelésen
        for (Order order : orders) {
            // Lekérjük a rendelés hónapját
            String month = order.getOrderDate().format(formatter);

            // Ha az adott hónap még nincs a listában, hozzáadjuk alapértékkel
            if (!months.contains(month)) {
                months.add(month);
                totals.add(0.0);
            }

            // Megkeressük a hónap indexét
            int index = months.indexOf(month);
            double currentTotal = totals.get(index);

            // Még egyszer végigmegyünk az összes rendelésen, hogy az adott hónaphoz tartozókat összegezzük
            for (Order order1 : orders) {
                String month1 = order1.getOrderDate().format(formatter);
                // Ha az aktuálisan vizsgált rendelés ugyanarra a hónapra esik, hozzáadjuk az összeghez
                if (month1.equals(month)) {
                    totals.set(index, currentTotal + order1.getTotalAmount());
                    currentTotal = totals.get(index);
                }
            }
        }

        // Eredménylista összeállítása a hónapokkal és az összegekkel
        List<String[]> result = new ArrayList<>();
        for (int i = 0; i < months.size(); i++) {
            result.add(new String[]{months.get(i), String.format("%.2f", totals.get(i))});
        }

        // Rendezzük hónap szerint növekvő sorrendbe
        result.sort(Comparator.comparing(a -> a[0]));

        return result;
    }

    public static List<String[]> getTotalAmountByCountryAndMonth(List<Customer> customers, List<Order> orders) {
        // Formátum beállítása (2025-04)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        // Kinyerjük az összes országot és customerId-t
        List<String> countries = new ArrayList<>();
        List<Integer> customerIds = new ArrayList<>();
        for (Customer customer : customers) {
            countries.add(customer.getCountry());
            customerIds.add(customer.getCustomerId());
        }

        // A kulcsok (ország + hónap) és az ezekhez tartozó összegek listája
        List<String> keys = new ArrayList<>();
        List<Double> totals = new ArrayList<>();

        // Végigmegyünk az összes rendelésen
        for (Order order : orders) {
            String country = "";

            // Kikeressük, hogy az adott rendelés melyik országból érkezett (customer alapján)
            for (Customer customer : customers) {
                if (customer.getCustomerId() == order.getCustomerId()) {
                    country = customer.getCountry();
                    break;
                }
            }

            // Lekérjük a hónapot, és összeállítjuk a kulcsot (pl. "Hungary-2025-04")
            String month = order.getOrderDate().format(formatter);
            String key = country + "-" + month;

            // Ha ez a kulcs még nincs a listában, hozzáadjuk 0.0-val
            if (!keys.contains(key)) {
                keys.add(key);
                totals.add(0.0);
            }

            // Az összeg hozzáadása a megfelelő kulcshoz
            int index = keys.indexOf(key);
            totals.set(index, totals.get(index) + order.getTotalAmount());
        }

        // Eredménylista összeállítása: ország, hónap, összeg
        List<String[]> result = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            String[] parts = keys.get(i).split("-");
            String country = parts[0];
            String month = parts[1] + "-" + parts[2]; // visszaalakítás yyyy-MM formátumra
            result.add(new String[]{country, month, String.format("%.2f", totals.get(i))});
        }

        // Rendezzük először ország, majd hónap szerint
        result.sort(Comparator.comparing((String[] a) -> a[0]).thenComparing(a -> a[1]));

        return result;
    }

}
