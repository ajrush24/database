package employee.management.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import employee.management.entity.Employer;

public interface EmployerDao extends JpaRepository<Employer, Long> {

}
