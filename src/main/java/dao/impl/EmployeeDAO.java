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
public class EmployeeDAO implements DAO<Employee, String> {
	
	@Inject
	 private Connection connection;
	
	@Override
	public Employee findById(String id) {
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
            throw new RuntimeException("Lỗi khi lấy danh sách nhân viên: " + e.getMessage());
        }
	}

	@Override
	public Employee create(Employee t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee updateById(String id, Employee employee) {
		String sql = "UPDATE employees SET employee_name=?, employee_age=?, date_of_birth=? WHERE employee_code=?;";
        try (
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            
            pstmt.setString(1, employee.getName());
            pstmt.setInt(2, employee.getAge());
            pstmt.setDate(3, employee.getDateOfBirth());
            pstmt.setString(4, employee.getCode());
            pstmt.ex
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public boolean delete(Employee t) {
		// TODO Auto-generated method stub
		return false;
	}

	private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setCode(rs.getString("employee_code"));
        employee.setName(rs.getString("employee_name"));
        employee.setAge(rs.getInt("employee_age"));
        employee.setDateOfBirth(rs.getDate("date_of_birth"));
        return employee;
    }
	
}
