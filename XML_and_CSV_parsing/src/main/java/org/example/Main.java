package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        XMLWorker xmlWorker = new XMLWorker();
        CSVWorker csvWorker = new CSVWorker();
        Scanner in = new Scanner(System.in);
        System.out.println("xml - 1, csv - 2");
        String filepath = "C:\\address.";
        String str = in.next();

        long startTime = System.currentTimeMillis();
        if (str.equals("1"))
            xmlWorker.ParseXML(filepath + "xml");
        else
            csvWorker.ParseCSV(filepath + "csv");

        long endTime = System.currentTimeMillis();
        System.out.println("speed time: " + (endTime - startTime) + "ms");
    }
}