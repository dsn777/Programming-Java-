package org.example;

import lombok.Data;
import java.util.Date;

@Data
public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String department;
    private int salary;

    @Override
    public String toString() {
        return "id = " + id + "\n" +
                "firstName = " + firstName + "\n" +
                "lastName = " + lastName + "\n" +
                "birthDate = " + birthDate + "\n" +
                "department = " + department + "\n" +
                "salary = " + salary;
    }
}
