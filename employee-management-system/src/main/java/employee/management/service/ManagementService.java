package employee.management.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import employee.management.controller.model.EmployerData;
import employee.management.dao.EmployerDao;
import employee.management.entity.Employer;

@Service
public class ManagementService {
	
	@Autowired
	private EmployerDao employerDao;

	@Transactional(readOnly = false)
	public EmployerData saveEmployer(EmployerData employerData) {
		Employer employer = employerData.toEmployer();
		Employer dbEmployer = employerDao.save(employer);
		
		return new EmployerData(dbEmployer);
	}

	@Transactional(readOnly = true)
	public EmployerData retrieveEmployerById(Long employerId) {
		Employer employer = findEmployerById(employerId);
		return new EmployerData(employer);

	}

	private Employer findEmployerById(Long employerId) {
		return employerDao.findById(employerId)
				.orElseThrow(() -> new NoSuchElementException(
						"Employer with the ID: " + employerId + " was not found."));
	}

	@Transactional(readOnly = true)
	public List<EmployerData> retrieveAllEmployers() {
		List<Employer> employerEntities = employerDao.findAll();
		List<EmployerData> employerDtos = new LinkedList<>();
		employerEntities.sort((emp1, emp2) -> emp1.getEmployerName().compareTo(emp2.getEmployerName()));
		for(Employer employer : employerEntities) {
			EmployerData employerData = new EmployerData(employer);
			employerDtos.add(employerData);
		}
		
		return employerDtos;
	}

	@Transactional(readOnly = false)
	public void deleteEmployer(Long employerId) {
		Employer employer = findEmployerById(employerId);
		employerDao.delete(employer);
	}
}
	
