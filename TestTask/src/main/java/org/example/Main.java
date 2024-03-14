package org.example;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/*
Используется MySQL,
База данных MyDataBase и таблица Employee создаются, если они не созданы,
после чего в таблицу добавляется 4 сотрудника
 */


public class Main {
    private static final String MENU_TEXT = "Select the command: \n1 - Group by name \n2 - Find by ID \n3 - Find between\n4 - Get all employees\n5 - Exit";

    public static void main(String[] args) throws SQLException {
        EmployeeService service = new EmployeeService();
        Scanner scanner = new Scanner(System.in);

        int selectedIndex = 0;
        while (selectedIndex != 5) {
            try {
                System.out.println(MENU_TEXT);
                System.out.print("Input the command number: ");
                selectedIndex = Integer.parseInt(String.valueOf(scanner.nextLine()));
                switch (selectedIndex) {
                    case 1:
                        System.out.println("\nGrouped by Name:\n------");
                        System.out.print(service.groupByName());
                        break;

                    case 2:
                        System.out.print("Enter employee ID: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.println("\nFound by ID:\n------");
                        System.out.println(service.findById(id));
                        break;

                    case 3:
                        System.out.print("Enter the date in YYYY-MM-DD format: ");
                        Date date1 = Date.valueOf(scanner.nextLine());

                        System.out.print("Enter the date in YYYY-MM-DD format: ");
                        Date date2 = Date.valueOf(scanner.nextLine());

                        System.out.println("\nFound between:\n------");
                        List<Employee> list = service.findBetween(date1, date2);
                        for (Employee employee : list)
                            System.out.println(employee + "\n");
                        break;
                    case 4:
                        System.out.println("All employees:\n------");
                        for (Employee emp : service.getAllEmployees())
                            System.out.println(emp + "\n");
                    case 5:
                        break;

                    default:
                        System.out.println("Invalid command!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format!");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format!");
            }
            System.out.println();
        }
    }
}