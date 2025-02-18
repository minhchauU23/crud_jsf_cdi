package beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.impl.EmployeeDAO;
import model.Employee;

@Named("ebs")
@SessionScoped
public class EmployeesBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Employee employee;
	private List<Employee> employees;
	
	@Inject
	private EmployeeDAO employeeDAO; // Ensure CDI injects it properly

	@PostConstruct
	public void init() {
		employees = employeeDAO.findAll(); // Fetch employees after CDI injects the DAO
	}
	
	public EmployeesBean() {
		employee = new Employee();
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public String navigateCreatePage() {
		this.employee = new Employee();
		return "add";
	}
	
	public String create() {
		LocalDate birthDate = employee.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    LocalDate currentDate = LocalDate.now();
		employee.setAge(Period.between(birthDate, currentDate).getYears());
		employees.add(employeeDAO.create(employee));
		return "index";
	}
	
	public String navigateUpdatePage(Employee employee) {
		this.employee = employee;
		return "update";
	}
	
	public String update() {
		LocalDate birthDate = employee.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	    LocalDate currentDate = LocalDate.now();
		employee.setAge(Period.between(birthDate, currentDate).getYears());
		employeeDAO.updateById(employee.getCode(), employee);
		return "index";
	}
	
	public String delete(Employee employee) throws Exception {
		this.employee = employee;
		if(employeeDAO.delete(employee)) {
			employees.remove(employee);
			employee = new Employee();
		}
		return "";
	}
	
}
