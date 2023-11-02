package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();

        XMLWorker xmlWorker = new XMLWorker();
        xmlWorker.ParseXML2("C:\\Users\\rev93\\Desktop\\ЛЭТИ\\5 семестр\\Программирование Java\\2 лаба\\laba2\\src\\main\\resources\\address.xml");

        long midTime = System.currentTimeMillis();

        //CSVWorker csvWorker = new CSVWorker();
        //csvWorker.ParseCSV("C:\\Users\\rev93\\Desktop\\address.csv");

        long endTime = System.currentTimeMillis();

        System.out.println("xml: " + (midTime - startTime) + "ms");
        System.out.println("csv: " + (endTime - midTime) + "ms");
    }
}
