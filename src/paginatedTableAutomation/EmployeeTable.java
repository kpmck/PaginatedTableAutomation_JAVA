package paginatedTableAutomation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EmployeeTable {

	private WebDriver _driver;
	public By employeeTable = By.id("myTable");
	public By nextPage = By.className("next_link");
	public By headers = By.xpath("./ancestor::*//th");
	public By rows = By.xpath("./tr[not(contains(@style,'none'))]");

	public EmployeeTable(WebDriver driver) {
		_driver = driver;
	}

	public class Employee {
		private WebElement Checkbox;
		private WebElement ID;
		private WebElement FirstName;
		private WebElement LastName;
		private WebElement Job;

		public WebElement getCheckbox() {
			return Checkbox;
		}

		public void setCheckbox(WebElement checkbox) {
			Checkbox = checkbox;
		}

		public WebElement getID() {
			return ID;
		}

		public void setID(WebElement iD) {
			ID = iD;
		}

		public WebElement getFirstName() {
			return FirstName;
		}

		public void setFirstName(WebElement firstName) {
			FirstName = firstName;
		}

		public WebElement getLastName() {
			return LastName;
		}

		public void setLastName(WebElement lastName) {
			LastName = lastName;
		}

		public WebElement getJob() {
			return Job;
		}

		public void setJob(WebElement job) {
			Job = job;
		}

	}
	
	public Employee getEmployee(String columnName, String columnValue) throws Exception
	{
		return searchEmployeeTable(columnName, columnValue).get(0);
	}

	private List<Employee> searchEmployeeTable(String columnName, String columnValue) throws Exception {
		return queryDataTable(employeeTable, nextPage, columnName, columnValue);
	}

	private List<Employee> queryDataTable(By employeetable, By nextPage, String columnName, String columnValue) throws Exception{
		ArrayList<Employee> tableData = getTableData(employeeTable);
		WebElement nextPageButton = _driver.findElement(nextPage);
		List<Employee> results = parseResults(tableData, columnName, columnValue);
		
		while (results.isEmpty()) {
			tableData = getTableData(employeeTable);
			results = parseResults(tableData, columnName, columnValue);
			if(!results.isEmpty() ||  !nextPageButton.isDisplayed())
				break;
			nextPageButton.click();
		}
		return results;
	}

	private ArrayList<Employee> getTableData(By employeeTable) {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		List<WebElement> tableRows = _driver.findElement(employeeTable).findElements(rows);

		for (int i = 0; i < tableRows.size(); i++) {
			List<WebElement> rowCells = tableRows.get(i).findElements(By.tagName("td"));
			Employee employee = new Employee();
			for (int j = 0; j < rowCells.size(); j++) {
				employee.setCheckbox(rowCells.get(0));
				employee.setID(rowCells.get(1));
				employee.setFirstName(rowCells.get(2));
				employee.setLastName(rowCells.get(3));
				employee.setJob(rowCells.get(4));
			}
			employees.add(employee);
		}
		return employees;
	}
	
	private List<Employee> parseResults(ArrayList<Employee> tableData, String columnName, String columnValue) throws Exception{
		switch (columnName.toLowerCase()) {
		case ("id"):
			return tableData.stream().filter(x -> x.ID.getText().equals(columnValue)).collect(Collectors.toList());
		case ("firstname"):
			return tableData.stream().filter(x -> x.FirstName.getText().equals(columnValue))
					.collect(Collectors.toList());
		case ("lastname"):
			return tableData.stream().filter(x -> x.LastName.getText().equals(columnValue))
					.collect(Collectors.toList());
		case ("job"):
			return tableData.stream().filter(x -> x.Job.getText().equals(columnValue))
					.collect(Collectors.toList());
		default:
			throw new Exception(String.format("Supplied column name %s does not exist for the table.", columnName));
		}		
	}
}
