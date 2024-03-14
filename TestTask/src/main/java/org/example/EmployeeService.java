package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.CreatingQuery.*;

public class EmployeeService {
    private static Connection connection;
    private static Statement statement;

    static {
        try {
            connection = DriverManager.getConnection(ConnectionData.HOST, ConnectionData.USER, ConnectionData.PASSWORD);
            statement = connection.createStatement();
            System.out.println("Host connection established successfully!");
        } catch (SQLException e) {
            System.out.println("Error  " + e.getErrorCode() + " when connecting to the database.");
        }
    }

    public EmployeeService() throws SQLException {
        statement.addBatch(CREATE_DATABASE_Query);
        statement.addBatch(CREATE_DATABASE_Query);
        statement.addBatch(USE_DB_Query);
        statement.addBatch(CREATE_TABLE_Query);
        statement.executeBatch();

        if (!statement.executeQuery(SELECT_Query).next())
            statement.executeUpdate(INSERT_Employees_Query);
    }

    private static Employee resultSetToEmployee(ResultSet set) throws SQLException {
        Employee employee = new Employee();

        employee.setId(set.getInt("id"));
        employee.setFirstName(set.getString("firstName"));
        employee.setLastName(set.getString("lastName"));
        employee.setBirthDate(set.getDate("birthDate"));
        employee.setDepartment(set.getString("department"));
        employee.setSalary(set.getInt("salary"));

        return employee;
    }
    public List<Employee> getAllEmployees() throws SQLException {
        ResultSet resultSet = statement.executeQuery(SELECT_Query);
        List<Employee> EmployeesList = new ArrayList<>();

        while(resultSet.next())
            EmployeesList.add(resultSetToEmployee(resultSet));

        return EmployeesList;
    }
    public String groupByName() throws SQLException {
        String GROUP_BY_Query = "SELECT firstName, COUNT(FirstName) as Number FROM Employee GROUP BY firstName";
        ResultSet resultSet = statement.executeQuery(GROUP_BY_Query);
        StringBuilder builder = new StringBuilder();

        while (resultSet.next())
            builder.append(resultSet.getString("firstName")).append(": ").
                    append(resultSet.getString("Number")).
                    append("\n");

        return builder.toString();
    }

    public Employee findById(int id) throws SQLException {
        String FIND_BY_ID_Query = "SELECT * FROM Employee WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_Query);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next() ? resultSetToEmployee(resultSet) : null;
    }

    public List<Employee> findBetween(Date date1, Date date2) throws SQLException {

        if (date1.after(date2)) {
            Date tmp = date1;
            date1 = date2;
            date2 = tmp;
        }

        String findBetweenQuery = "Select * from Employee " +
                "WHERE birthDate between ? AND ?";
        PreparedStatement preparedStatement = connection.prepareStatement(findBetweenQuery);
        preparedStatement.setDate(1, date1);
        preparedStatement.setDate(2, date2);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Employee> findedList = new ArrayList<>();

        while(resultSet.next())
            findedList.add(resultSetToEmployee(resultSet));

        return findedList;
    }
}
