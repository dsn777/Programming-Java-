package org.example;

import javax.xml.bind.annotation.*;
public class Data {
    @XmlAttribute(name = "city")
    private String city;
    @XmlAttribute(name = "street")
    private String street;
    @XmlAttribute(name = "house")
    private int house;
    @XmlAttribute(name = "floor")
    private int floor;

    public String getCity(){ return city;}
    public String getStreet(){
        return street;
    }
    public int getHouse(){
        return house;
    }
    public int getFloor(){
        return floor;
    }

    /*final StringBuilder sb = new StringBuilder("Book{");
        sb.append("name='").append(name).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", publisher='").append(publisher).append('\'');
        sb.append(", isbn='").append(isbn).append('\'');
        sb.append('}');*/

    public String toString(){
        final StringBuilder sb = new StringBuilder();
        sb.append(city);
        sb.append(street);
        sb.append(house);
        sb.append(floor);
        return sb.toString();
    }

}


