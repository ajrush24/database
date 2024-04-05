package employee.management.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import employee.management.controller.model.EmployerData;
import employee.management.service.ManagementService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/employee_management")
@Slf4j
public class ManagementController {
	@Autowired
	private ManagementService managementService;

	@PostMapping("/employer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public EmployerData createEmployer(@RequestBody EmployerData employerData) {
		log.info("Creating Employer {}", employerData);
		return managementService.saveEmployer(employerData);
	}
	
	@PutMapping("/employer/{employerId}")
	public EmployerData updateEmployer(@PathVariable Long employerId, @RequestBody EmployerData employerData) {
		employerData.setEmployerId(employerId);
		log.info("Updating Employer {}", employerData);
		return managementService.saveEmployer(employerData);
	}
	
	@GetMapping("/employer/{employerId}")
	public EmployerData retrieveEmployer(@PathVariable Long employerId) {
		log.info("Retrieving employer with Id={}", employerId);
		return managementService.retrieveEmployerById(employerId);
	}
	
	@GetMapping("/employer/{employerId}")
	public EmployerData retrieveEmployer1(@PathVariable Long employerId) {
		log.info("Retrieving employer with ID = {}", employerId);
		return managementService.retrieveEmployerById(employerId);
	}
	
	@GetMapping("/employer")
	public List<EmployerData> retrieveAllEmployers(){
		log.info("Retrieving all Employers");
		return managementService.retrieveAllEmployers();
	}
	
	@DeleteMapping("/employer/{employerId}")
	public Map<String, String> deleteEmployer(@PathVariable Long employerId) {
		log.info("Deleting employer with ID=" + employerId + ".");
		managementService.deleteEmployer(employerId);
		
		return Map.of("message", "Employer with ID=" + employerId + " was deleted sucessfully.");
}

}
