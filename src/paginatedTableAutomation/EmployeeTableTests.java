package paginatedTableAutomation;

import static org.junit.jupiter.api.Assertions.*;

import paginatedTableAutomation.EmployeeTable.Employee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeTableTests extends BaseTestClass {

	EmployeeTable employeeTable;

	@BeforeEach
	public void refreshEmployeeTable()
	{
		_driver.navigate().refresh();
		employeeTable = new EmployeeTable(_driver);					
	}

	@Test
	public void SearchByFirstName() throws Exception {
		Employee employee = employeeTable.getEmployee("FirstName", "Jean");
		assertEquals(employee.getFirstName().getText(), "Jean");
	}

	@Test
	public void SearchByLastName() throws Exception {
		Employee employee = employeeTable.getEmployee("LastName", "Maddox");
		assertEquals(employee.getFirstName().getText(), "Jamie");
	}
	
}
