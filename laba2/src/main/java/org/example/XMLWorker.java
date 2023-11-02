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

/*
    public List<String> uniqueItems(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] charText = text.toCharArray();
        HashMap<String, Integer> dupLines = new HashMap<>();

        String item;
        for (char symbol : charText) {
            stringBuilder.append(symbol);
            if (symbol == '>') {
                item = stringBuilder.toString();
                if (dupLines.containsKey(item)) {
                    dupLines.put(item, dupLines.get(item) + 1);
                } else
                    dupLines.put(stringBuilder.toString(), 1);

                stringBuilder.delete(0, stringBuilder.length());
            }
        }

        // Сортировка хавает время...
        // Вместо этого куска кода можно попробовать создать другую карту, и закидывать туда повторки, но хзз..
        // Либо на этапе проверки повтора закидывать item в какой-нибудь List, а затем просто пройтись по карте foreach`ем, где аргументом будут элементы в list`e, хз...
        HashMap<String, Integer> sortedMap = dupLines.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                .collect(Collectors.toMap(
                        HashMap.Entry::getKey,
                        HashMap.Entry::getValue,
                        (a, b) -> {
                            throw new AssertionError();
                        },
                        LinkedHashMap::new
                ));

        return sortedMap.keySet().stream().toList();
    }

    public void ParseXML(String filename) throws Exception {
        DocumentBuilderFactory DocBF = DocumentBuilderFactory.newInstance();
        Document document = DocBF.newDocumentBuilder().parse(new File(filename));
        NamedNodeMap attrList;
        NodeList itemList = document.getFirstChild().getChildNodes();

        HashMap<String, HashMap<String, Integer>> ParseRes = new HashMap<>();
        HashMap<String, Integer> buildingsNum;
        String city, floor;

        for (int i = 0; i < itemList.getLength(); i++) {
            if (itemList.item(i).getNodeType() != Node.ELEMENT_NODE)
                continue;

            attrList = itemList.item(i).getAttributes();
            city = attrList.getNamedItem("city").getNodeValue();
            floor = attrList.getNamedItem("floor").getNodeValue();
            buildingsNum = ParseRes.get(city);

            if (buildingsNum != null) {
                if (buildingsNum.containsKey(floor)) {
                    int count = buildingsNum.get(floor);
                    buildingsNum.put(floor, count + 1);
                } else
                    buildingsNum.put(floor, 1);
            } else {
                buildingsNum = new HashMap<>();
                buildingsNum.put(floor, 1);
                ParseRes.put(city, buildingsNum);
            }
        }
        System.out.println();
        //dubli.put(line, (count == null) ? 1 : count + 1);
    }*/
