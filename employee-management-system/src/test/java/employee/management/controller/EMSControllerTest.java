package employee.management.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.IntPredicate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import employee.management.EmployeeManagementApplication;
import employee.management.controller.model.EmployerData;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = EmployeeManagementApplication.class)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sq;"})
@SqlConfig(encoding = "utf-8")
class EMSControllerTest extends EMSTestSupport {


	@Test
	void testInsertEmployer() {
		// Given : Employer Requestt
		EmployerData request = buildInsertEmployer(1);
		EmployerData expected = buildInsertEmployer(1);
		//When: Employer is added to employer table
		EmployerData actual = insertEmployer(request);
		//Then: The employer returned is what is expected
		assertThat(actual).isEqualTo(expected);
		//And: there is one row in the employer table.
		assertThat(rowsInEmployerTable()).isOne();
		
	}
	
	@Test
	void testRetrieveEmployer() {
		
		EmployerData employer = insertEmployer(buildInsertEmployer(1));
		EmployerData expected = buildInsertEmployer(1);
		
		EmployerData actual = retrieveEmployer(employer.getEmployerId());
		
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void testRetrieveAllEmployers() {
		List<EmployerData> expected = insertTwoEmployers();
		
		List<EmployerData> actual = retrieveAllEmployers();
		
		assertThat(sorted(actual)).isEqualTo(sorted(expected));
	}
	
	@Test
	void testUpdateEmployer() {
		insertEmployer(buildInsertEmployer(1));
		EmployerData expected = buildUpdateEmployer();
		EmployerData actual = updateEmployer(expected);
		assertThat(actual).isEqualTo(expected);
		assertThat(rowsInEmployerTable()).isOne();
		
	}

	@Test
	void testDeleteEmployerWithEmployees() {
		EmployerData employer = insertEmployer(buildInsertEmployer(1));
		Long employerId = employer.getEmployerId();
		
		insertEmployee(1);
		insertEmployee(2);
		
		assertThat(rowsInEmployerTable()).isOne();
		assertThat(rowsInEmployeeTable()).isEqualTo(2);
		assertThat(rowsInEmployeeDepartmentTable()).isEqualTo(4);
		int departmentRows = rowsInDepartmentTable();
		
		deleteEmployer(employerId);
		
		assertThat(rowsInEmployerTable()).isZero();
		assertThat(rowsInEmployeeTable()).isZero();
		assertThat(rowsInDepartmentTable()).isZero();
		assertThat(rowsInDepartmentTable()).isEqualTo(departmentRows);
	}

	protected void deleteEmployer(Long employerId) {
		
	}

	

	
	
}
