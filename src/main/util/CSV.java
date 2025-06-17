package main.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple utility class for reading and writing CSV files. 
 */
public class CSV {

    /**
     * Reads a CSV file and returns its content as a List of String arrays (each array is a row).
     * This method assumes that the CSV is simple and does not handle quoted fields or commas within fields.
     * @param filePath The path to the CSV file to read.
     * @return A List of String arrays, where each array represents a row in the CSV file.
     * @throws IOException
     */
    public static List<String[]> readCSV(String filePath) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (var br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }
        }
        return data;
    }

    /**
     * Writes a List of String arrays to a CSV file.
     * Each array represents a row in the CSV.
     * This method does not handle special cases like commas in fields or quoted strings.
     * @param filePath The path to the output CSV file.
     * @param data The List of String arrays to write to the CSV file.
     * @throws IOException
     */
    public static void writeCSV(String filePath, List<String[]> data) throws IOException {
        try (var bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        }
    }
}
