DROP TABLE IF EXISTS position;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS employer;

CREATE TABLE employer(
	employer_id int NOT NULL AUTO_INCREMENT,
	employer_name varchar(256) NOT NULL,
	employer_address varchar(128) NOT NULL,
	phone_number varchar(30
	city varchar(60),
	state varchar(40),
	zip varchar(30)
	PRIMARY KEY (employer_id)
);

CREATE TABLE employee (
	employee_id int NOT NULL AUTO_INCREMENT,
	employer_id int NULL,
	name varchar(60) NOT NULL,
	age int,
	gender varchar(128),
	PRIMARY KEY(employee_id),
	FOREIGN KEY (employer_id) REFERENCES employer (employer_id) ON DELETE CASCADE
);
CREATE TABLE department (
	department_id int NOT NULL AUTO_INCREMENT,
	name varchar(128),
	PRIMARY KEY(department_id)
);

CREATE TABLE position (
	employee_id int NOT NULL,
	department_id int NOT NULL,
	FOREIGN KEY (employee_id) REFERENCES employee (employee_id) ON DELETE CASCADE,
	FOREIGN KEY (department_id) REFERENCES department (department_id) ON DELETE CASCADE,
	
);