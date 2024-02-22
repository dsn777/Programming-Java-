package org.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class CSVWorker {
    public void printDuplicates(HashMap<String, Integer> duplicates) {
        for (Map.Entry<String, Integer> entry : duplicates.entrySet()) {
            if (entry.getValue() != 1) {
                System.out.println("\"" + entry.getKey() + " - Количество повторов: " + entry.getValue());
            }
        }
    }

    public void printParseResult(HashMap<String, HashMap<String, Integer>> Result) {
        HashMap<String, Integer> temp;
        for (String city : Result.keySet()) {
            System.out.println(city);
            temp = Result.get(city);
            for (String floor : temp.keySet()) {
                System.out.println("\t" + floor + " - " + temp.get(floor));
            }
        }
    }
    public void ParseCSV(String filepath) throws Exception {
        HashMap<String, Integer> addressMap = new HashMap<>();
        HashMap<String, HashMap<String, Integer>> ParseResult = new HashMap<>();
        HashMap<String, Integer> buildingsNum;

        InputStreamReader fileReader = new InputStreamReader(new FileInputStream(filepath));
        CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();
        List<String[]> allData = csvReader.readAll();
        csvReader.close();

        Integer count;
        for (String[] Data : allData) {
            for (String DataElem : Data) {
                count = addressMap.get(DataElem);
                addressMap.put(DataElem, (count == null) ? 1 : count + 1);
            }
        }

        for (String line : addressMap.keySet()) {
            String[] splitedLine = line.split(";");
            buildingsNum = ParseResult.get("\"" + splitedLine[0]);

            if (buildingsNum != null) {
                count = buildingsNum.get(splitedLine[3]);
                buildingsNum.put(splitedLine[3], (count == null) ? 1 : count + 1);
            } else {
                buildingsNum = new HashMap<>();
                buildingsNum.put(splitedLine[3], 1);
            }
            ParseResult.put("\"" + splitedLine[0], buildingsNum);
        }

        printDuplicates(addressMap);
        printParseResult(ParseResult);
    }
}

