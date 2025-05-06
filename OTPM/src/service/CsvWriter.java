package service;

import model.Customer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    // CSV fájlba írás
    public static void writeToCsv(String filename, List<String[]> rows, String[] header){

        BufferedWriter writer = null;

        try{
            // Fájl megnyitása
            writer = new BufferedWriter(new FileWriter(filename));
            // Fejléc
            writer.append(String.join(",", header));
            writer.append("\n");

            // Sorok kiírása
            for (String[] row : rows){
                writer.append(String.join(",", row));
                writer.append("\n");
            }
        } catch(Exception e){
            // Hiba esetén log
            e.printStackTrace();
        } finally{
            try {
                // Fájl bezárása
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
