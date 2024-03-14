package org.example;

public class CreatingQuery {
    public final static String CREATE_DATABASE_Query = "CREATE DATABASE IF NOT EXISTS " + ConnectionData.DATABASE;
    public final static String USE_DB_Query = "USE " + ConnectionData.DATABASE;
    public final static String SELECT_Query = "SELECT * from Employee";
    public final static String CREATE_TABLE_Query = "CREATE TABLE IF NOT EXISTS Employee(" +
            "ID Int auto_increment," +
            "firstName VARCHAR(20)," +
            "lastName VARCHAR(20)," +
            "birthDate DATE," +
            "department VARCHAR(45)," +
            "salary INT," +
            "primary key(ID));";

    public final static String INSERT_Employees_Query = "INSERT INTO Employee (firstName, lastName, birthDate, department, salary) " +
            "VALUES " +
            "('Aleksandr', 'Aleksandrov', '1960-11-30', 'Development department', 150000)," +
            "('Vasya', 'Petrov',\t'2001-10-03','HR',70000)," +
            "('Vladimir','Sidorov','1985-03-03','Development',150000)," +
            "('Vasya','Ivanov','2000-12-18',\t'Testing Department',100000);";

}
