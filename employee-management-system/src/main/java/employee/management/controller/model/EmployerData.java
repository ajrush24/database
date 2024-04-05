package employee.management.controller.model;

import java.util.HashSet;
import java.util.Set;
import employee.management.entity.Department;
import employee.management.entity.Employee;
import employee.management.entity.Employer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public class EmployerData {
	private Long employerId;
	private String employerName;
	private String employerAddress;
	private String phoneNumber;
	private String city;
	private String state;
	private String zip;
	private Set<EmployeeData> employees = new HashSet<>();

	public EmployerData(Employer employer) {
		this.employerId = employer.getEmployerId();
		this.employerName = employer.getEmployerName();
		this.employerAddress = employer.getEmployerAddress();
		this.phoneNumber = employer.getPhoneNumber();
		this.city = employer.getCity();
		this.state = employer.getState();
		this.zip = employer.getZip();

		for (Employee employee : employer.getEmployees()) {
			this.employees.add(new EmployeeData(employee));
		}
	}

	public EmployerData(Long employerId, String employerName, String employerAddress, String phoneNumber, String city,
			String state, String zip) {
		this.employerId = employerId;
		this.employerName = employerName;
		this.employerAddress = employerAddress;
		this.phoneNumber = phoneNumber;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	public Employer toEmployer() {
		Employer employer = new Employer();

		employer.setEmployerId(employerId);
		employer.setEmployerName(employerName);
		employer.setEmployerAddress(employerAddress);
		employer.setPhoneNumber(phoneNumber);
		employer.setCity(city);
		employer.setState(state);
		employer.setZip(zip);

		for (EmployeeData employeeData : employees) {
			employer.getEmployees().add(employeeData.toEmployee());
		}
		return employer;

	}

	@Data
	@NoArgsConstructor
	public class EmployeeData {
		private Long employeeId;
		private String name;
		private int age;
		private String gender;
		private Employer employer;
		private Set<Department> departments = new HashSet<>();

		public EmployeeData(Employee employee) {
			this.employeeId = employee.getEmployeeId();
			this.name = employee.getName();
			this.age = employee.getAge();
			this.gender = employee.getGender();
			this.employer = employee.getEmployer();

			for (Department department : employee.getDepartments()) {
				this.departments.add (department);
			}
		}

		public Employee toEmployee() {
			Employee employee = new Employee();
			employee.setEmployeeId(employeeId);
			employee.setName(name);
			employee.setAge(age);
			employee.setGender(gender);

			for (DepartmentData departmentData : departments) {
				employee.getDepartments().add(departmentData.toDepartment());
			}

			return employee;
		}
	}

	@Data
	@NoArgsConstructor
	public class DepartmentData {
		private Long departmentId;
		private String name;

		public DepartmentData(Department department) {
			this.departmentId = department.getDepartmentId();
			this.name = department.getName();
		}

		public Department toDepartment() {
			Department department = new Department();

			department.setDepartmentId(departmentId);
			department.setName(name);

			return department;
		}
	}
}
