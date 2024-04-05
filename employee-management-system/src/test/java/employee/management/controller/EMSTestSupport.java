package employee.management.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.function.IntPredicate;
import org.glassfish.jaxb.core.util.Which;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import employee.management.controller.model.EmployerData;
import employee.management.entity.Employer;

public class EMSTestSupport {

	private static final String EMPLOYEE_TABLE = "employee";
	private static final String EMPLOYEE_DEPARTMENT_TABLE = "employee_department";
	private static final String DEPARTMENT_TABLE = "department";
	private static final String EMPLOYER_TABLE = "employer";
	
	private static final String INSERT_EMPLOYEE_1_SQL = """
			INSERT INTO employee
			(employer_id, name, age, gender)
			VALUES (1, John McHannon, 34, Male) 
			""";

	private static final String INSERT_EMPLOYEE_2_SQL = """
			INSERT INTO employee
			(employer_id, name, age, gender)
			VALUES (2, Quentin Duff, 44, Male) 
			""";
	private static final String INSERT_DEPARTMENTS_1_SQL = """
			INSERT INTO department
			(employee_id, position_id)
			VALUES (23, 33) 
			""";
	private static final String INSERT_DEPARTMENTS_2_SQL = """
			INSERT INTO department
			(employee_id, position_id)
			VALUES (33, 42) 
			""";
	// @formatter:off
	private EmployerData insertAddress1 = new EmployerData(
		1L, 
		"JMM Law",
		"344 El Camino Way",
		"(205) 553-4173",
		"Albquerque",
		"New Mexico",
		"39485"
		);
	
	private EmployerData insertAddress2 = new EmployerData(
		2L, 
		"Star Platinum Games",
		"1520 S Birch Ln",
		"(876) 903-3568",
		"Austin",
		"Texas",
		"34578"
		);
	// @formatter:on

	private EmployerData updateAddress1 = new EmployerData(1L, "HHM Law", "3445 Durango Way", "(205) 455-3431",
			"Albquerque", "New Mexico", "39485");

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ManagementController managementController;

	protected EmployerData buildInsertEmployer(int i) {

		return which == 1 ? insertAddress1 : insertAddress2;
	}

	protected int rowsInEmployerTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, EMPLOYER_TABLE);
	}

	protected EmployerData insertEmployer(EmployerData employerData) {
		Employer employer = employerData.toEmployer();
		EmployerData clone = new EmployerData(employer);

		clone.setEmployerId(null);
		return ManagementController.createEmployer(clone);
	}

	protected List<EmployerData> insertTwoEmployers() {
		EmployerData employer1 = insertEmployer(buildInsertEmployer(1));
		EmployerData employer2 = insertEmployer(buildInsertEmployer(2));

		return List.of(employer1, employer2);
	}

	protected List<EmployerData> retrieveAllEmployers() {
		return managementController.retrieveAllEmployers();

	}

	protected List<EmployerData> sorted(List<EmployerData> list) {
		List<EmployerData> data = new LinkedList<>(list);
		data.sort((emp1, emp2) -> (int) (emp1.getEmployerId() - emp2.getEmployerId()));

		return data;
	}

	protected EmployerData updateEmployer(EmployerData expected) {
		return managementController.updateEmployer(employerData.getEmployerId(), locationData);
	}

	protected EmployerData buildUpdateEmployer() {
		return updateAddress1;
	}

	private void insertEmployee(int which) {
		String employeeSql = which == 1 ? INSERT_EMPLOYEE_1_SQL : INSERT_EMPLOYEE_2_SQL;
		String departmentSql = which == 1 ? INSERT_DEPARTMENTS_1_SQL : INSERT_DEPARTMENTS_2_SQL;

		jdbcTemplate.update(employeeSql);
		jdbcTemplate.update(departmentSql);
	}
	
	protected int rowsInDepartmentTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, DEPARTMENT_TABLE);
	}

	protected int rowsInEmployeeDepartmentTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, EMPLOYEE_DEPARTMENT_TABLE);
	}

	protected int rowsInEmployeeTable() {
		return JdbcTestUtils.countRowsInTable(jdbcTemplate, EMPLOYEE_TABLE);
	}

	protected void deleteEmployer(Long employerId) {
		managementController.deleteEmployer(employerId);
	}
}
