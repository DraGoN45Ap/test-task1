package main;

import main.service.OperationService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class.
 */
public class Main {

    /**
     * Variable for identify current user folder.
     */
    public static final String USER_DIR = System.getProperty("user.dir").concat( "\\");

    public static void main(String[] args) {
        if (args[0] == null) {
            System.out.println("Offices text file not found");
            return;
        }
        String officesFilename = args[0];
        File file = new File(USER_DIR.concat(officesFilename));
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            List<Integer> officesList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                officesList.add(Integer.valueOf(line));
            }
            OperationService.createOperationsList(officesList, Integer.valueOf(args[1]), args[2]);
        } catch (IOException e) {
            System.out.println("Cannot read file: " + officesFilename);
        }
    }
}
