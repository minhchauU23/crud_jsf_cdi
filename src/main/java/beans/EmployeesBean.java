package beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.impl.EmployeeDAO;
import model.Employee;

@Named("ebs")
@RequestScoped
public class EmployeesBean implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Employee selectedEmployee;
	private List<Employee> employees;
	

	@Inject
	private EmployeeDAO employeeDAO; // Ensure CDI injects it properly

	
	@PostConstruct
	public void init() {
		employees = employeeDAO.findAll(); // Fetch employees after CDI injects the DAO
	}
	
	public EmployeesBean() {
		
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}
	
	public String navigateUpdatePage(Employee employee) {
		this.selectedEmployee = employee;
		return "update";
	}
	
	public String update() {
		employeeDAO.updateById()
		return "index";
	}
	
	
}
