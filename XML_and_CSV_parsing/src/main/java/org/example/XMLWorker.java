package org.example;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;

public class XMLWorker {
    private void printDuplicates(HashMap<String, Integer> duplicates) {
        for (HashMap.Entry<String, Integer> entry : duplicates.entrySet()) {
            if (entry.getValue() != 1) {
                System.out.println("\"" + entry.getKey() + " - Количество повторов: " + entry.getValue());
            }
        }
    }
    private void printParseResult(HashMap<String, HashMap<String, Integer>> Result) {
        HashMap<String, Integer> temp;
        for (String city : Result.keySet()) {
            System.out.println(city);
            temp = Result.get(city);
            for (String floor : temp.keySet()) {
                System.out.println("\t" + floor + " - " + temp.get(floor));
            }
        }
    }
    public void ParseXML(String filepath) throws Exception {
        NamedNodeMap attrList;
        NodeList itemList = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filepath)).getFirstChild().getChildNodes();
        HashMap<String, HashMap<String, Integer>> ParseRes = new HashMap<>();
        HashMap<String, Integer> buildingsNum, duplicates = new HashMap<>();
        String city, street, house, floor;

        for (int i = 0; i < itemList.getLength(); i++) {
            if (itemList.item(i).getNodeType() != Node.ELEMENT_NODE)
                continue;

            attrList = itemList.item(i).getAttributes();
            city = attrList.getNamedItem("city").getNodeValue();
            house = attrList.getNamedItem("house").getNodeValue();
            street = attrList.getNamedItem("street").getNodeValue();
            floor = attrList.getNamedItem("floor").getNodeValue();

            String duplicate = city + " " + street + " " + house;

            if (!duplicates.containsKey(duplicate)) {
                duplicates.put(duplicate, 1);
                buildingsNum = ParseRes.get(city);

                if (buildingsNum != null) {
                    Integer count = buildingsNum.get(floor);
                    buildingsNum.put(floor, (count == null) ? 1 : count + 1);
                } else {
                    buildingsNum = new HashMap<>();
                    buildingsNum.put(floor, 1);
                }
                ParseRes.put(city, buildingsNum);
            } else
                duplicates.put(duplicate, duplicates.get(duplicate) + 1);
        }

        printDuplicates(duplicates);
        printParseResult(ParseRes);
    }
}

