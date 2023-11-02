package org.example;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class XML {
    public void printDubli(Map<String, Integer> dubli){
        for (Map.Entry<String, Integer> entry : dubli.entrySet()) {
            if (entry.getValue() !=1){
                System.out.println("Строка: " + "\"" + entry.getKey() + ", Количество повторов: " + entry.getValue());
            }
        }
    }
    public void printHouses(Map<String, Integer> houses){
        for (Map.Entry<String, Integer> entry : houses.entrySet()) {
            System.out.println("Город: " + entry.getKey() + ", Количество домов: " + entry.getValue());
        }
    }
    public void numOfHouses(Root root, Map<String, Integer> houses){
        for (Data item : root.datas) {
            String cur = (item.getCity() + ", этаж: " + item.getFloor());
            Integer count = houses.get(cur);
            houses.put(cur, (count == null) ? 1 : count + 1);
        }
        printHouses(houses);

    }
    public void Dubli(Root root, Map<String, Integer> dubli){
        for (Data item: root.datas) {
            Integer count = dubli.get(item.toString());
            dubli.put(item.toString(), (count == null) ? 1 : count + 1);
        }
        printDubli(dubli);
    }
    public Root Form(String file) throws IOException {
        try{
            InputStreamReader fileReader = new InputStreamReader(new FileInputStream(file));
            JAXBContext context = JAXBContext.newInstance(Root.class);
            Unmarshaller um = context.createUnmarshaller();
            return (Root) um.unmarshal(fileReader);
        }
        catch(Exception e){
            System.out.println("ooopp");
        }
        return null;
    }

}

