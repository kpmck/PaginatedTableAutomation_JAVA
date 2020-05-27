package paginatedTableAutomation;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTestClass {

	static WebDriver _driver;
	
	@BeforeAll
	public static void setUp() {
		WebDriverManager.chromedriver().setup();
		_driver = new ChromeDriver();
		_driver.navigate().to("C:\\eclipse-workspace\\PaginatedTableAutomation\\web\\EmployeeTable.html");
	}

	@AfterAll
	public static void tearDown() {
		_driver.quit();
	}
	
}
