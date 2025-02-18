package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.DAO;
import model.Employee;

@Named
@ApplicationScoped
public class EmployeeDAO implements DAO<Employee, Integer> {
	
	@Inject
	 private Connection connection;
	
	@Override
	public Employee findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> findAll()  {
		List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Mt_employee ORDER BY employee_code";
        
        try (Statement statement = connection.createStatement()){
        	ResultSet resultSet = statement.executeQuery(sql);
        	while (resultSet.next()) {
                employees.add(mapResultSetToEmployee(resultSet));
            }
            
            return employees;
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving employee list: " + e.getMessage());
        }
	}

	@Override
	public Employee create(Employee employee)  {
		 String sql = "INSERT INTO Mt_employee ( employee_name, employee_age, date_of_birth) VALUES ( ?, ?, ?) RETURNING employee_code";
	        
		  try (
		        PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
			  prepareStatement.setString(1, employee.getName());
			  prepareStatement.setInt(2, employee.getAge());
			  prepareStatement.setDate(3, new java.sql.Date(employee.getDateOfBirth().getTime()));
	            
	            try(ResultSet rs = prepareStatement.executeQuery()){
	            	if (rs.next()) {
	                    employee.setCode(rs.getInt("employee_code")); // Gán ID cho employee
	                    return employee;
	                }
	                throw new RuntimeException("Failed to add employee, could not get ID!");
	   
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException("Error when adding employee: " + e.getMessage());
	        }
	}

	@Override
	public Employee updateById(Integer id, Employee employee) {
		String sql = "UPDATE Mt_employee  SET employee_name=?, employee_age=?, date_of_birth=? WHERE employee_code=?;";
        try (
             PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            
        	prepareStatement.setString(1, employee.getName());
        	prepareStatement.setInt(2, employee.getAge());
        	prepareStatement.setDate(3, new java.sql.Date(employee.getDateOfBirth().getTime()));
        	prepareStatement.setInt(4, employee.getCode());
            int rowsAffected = prepareStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("No employee found with the given code: " + id);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
	}

	@Override
	public boolean delete(Employee employee) {
		String sql = "DELETE FROM Mt_employee WHERE employee_code = ?";

	    try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
	    	prepareStatement.setInt(1, employee.getCode());
	        int rowsAffected = prepareStatement.executeUpdate();
	        return rowsAffected > 0; 
	    } catch (SQLException e) {
	    	throw new RuntimeException("Error while deleting employee: " + e.getMessage());
	    }
	}

	private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setCode(rs.getInt("employee_code"));
        employee.setName(rs.getString("employee_name"));
        employee.setAge(rs.getInt("employee_age"));
        employee.setDateOfBirth(rs.getDate("date_of_birth"));
        return employee;
    }

	

	@Override
	public boolean existsById(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
