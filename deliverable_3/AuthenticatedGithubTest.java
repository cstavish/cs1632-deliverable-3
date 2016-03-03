package deliverable_3;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Test;

public class AuthenticatedGithubTest {

WebDriver authenticatedDriver;
	
	@Before
	public void setUpAuthenticatedDriver() {
		
		authenticatedDriver = new FirefoxDriver();
		
		authenticatedDriver.get("https://github.com/login");
		
		WebElement loginField = authenticatedDriver.findElement(By.id("login_field"));
		WebElement passwordField = authenticatedDriver.findElement(By.id("password"));
		
		loginField.sendKeys(System.getenv("GH_USERNAME"));
		passwordField.sendKeys(System.getenv("GH_PASSWORD"));
		
		authenticatedDriver.findElement(By.cssSelector("div.auth-form-body > input[name=\"commit\"]")).click();
		
	}
	
	@After
	public void tearDownAuthenticatedDriver() {
		
		authenticatedDriver.quit();
	}

	// STORY 3 TESTS
	
	@Test
	/*

	Given a repository,
		and a issue title that is an empty string
	When I attempt to create an issue on that repository using that issue title,
	Then the button to submit the new issue should be disabled.
	
	*/
	public void testStory3Scenario1() {
		
		authenticatedDriver.get("https://github.com/cs1632test/deliverable3_test/issues/new");
		
		authenticatedDriver.findElement(By.id("issue_title")).sendKeys("");
		
		assertFalse(authenticatedDriver.findElement(By.cssSelector("button.btn:nth-child(1)")).isEnabled());
	}
	
	@Test
	/*

	Given a repository,
		and a issue title that is an non-empty string,
		and an issue description that is an empty string,
	When I attempt to create an issue on that repository using that issue title and description,
	Then the issue should be created successfully.
	
	*/
	public void testStory3Scenario2() {
		
		authenticatedDriver.get("https://github.com/cs1632test/deliverable3_test/issues/new");
		
		authenticatedDriver.findElement(By.id("issue_title")).sendKeys(Math.random() + "");
		authenticatedDriver.findElement(By.id("issue_body")).sendKeys(Math.random() + "");
		
		authenticatedDriver.findElement(By.cssSelector("button.btn:nth-child(1)")).click();
		
		assertNotEquals(authenticatedDriver.getCurrentUrl(), "https://github.com/cs1632test/deliverable3_test/issues/new");
		
	}
	
	@Test
	/*

	Given a repository,
		and a issue title that is an non-empty string,
		and an assignee,
	When I attempt to create an issue on that repository using that issue title and assignee,
	Then the issue should be created successfully.
	
	*/
	public void testStory3Scenario3() {
		
		authenticatedDriver.get("https://github.com/cs1632test/deliverable3_test/issues/new");
		
		authenticatedDriver.findElement(By.id("issue_title")).sendKeys(Math.random() + "");
		
		authenticatedDriver.findElement(By.cssSelector("button.btn:nth-child(1)")).click();
		
		// assign self
		authenticatedDriver.findElement(By.cssSelector(".muted-link")).click();
		
		assertNotEquals(authenticatedDriver.getCurrentUrl(), "https://github.com/cs1632test/deliverable3_test/issues/new");
		
	}
	
	@Test
	/*

	Given a repository,
		and a issue title that is an non-empty string,
		and a label,
	When I attempt to create an issue on that repository using that issue title and label,
	Then the issue should be created successfully.

	*/
	public void testStory3Scenario4() {
		
		authenticatedDriver.get("https://github.com/cs1632test/deliverable3_test/issues/new");
		
		authenticatedDriver.findElement(By.id("issue_title")).sendKeys(Math.random() + "");
		
		authenticatedDriver.findElement(By.cssSelector("button.btn:nth-child(1)")).click();
		
		// assign "bug" label
		authenticatedDriver.findElement(By.cssSelector(".label-select-menu > button:nth-child(1)")).click();
		WebDriverWait wait = new WebDriverWait(authenticatedDriver, 1);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.color-label.labelstyle-fc2929")));
		authenticatedDriver.findElement(By.cssSelector("div.color-label.labelstyle-fc2929")).click();
		authenticatedDriver.findElement(By.cssSelector("div.modal-backdrop")).click();

		assertNotEquals(authenticatedDriver.getCurrentUrl(), "https://github.com/cs1632test/deliverable3_test/issues/new");
		
	}
	
	@Test
	/*

	Given a repository,
		and a issue title that is an non-empty string,
		and a issue description which contains Markdown text,
	When I attempt to create an issue on that repository using that issue title and description,
	Then the issue should be created successfully and the Markdown should be rendered in HTML.
	
	*/
	public void testStory3Scenario5() {
		
		authenticatedDriver.get("https://github.com/cs1632test/deliverable3_test/issues/new");
		
		authenticatedDriver.findElement(By.id("issue_title")).sendKeys(Math.random() + "");
		authenticatedDriver.findElement(By.id("issue_body")).sendKeys("*italic*");
		
		authenticatedDriver.findElement(By.cssSelector("button.btn:nth-child(1)")).click();
		
		assertTrue(authenticatedDriver.findElement(By.cssSelector(".markdown-format > p:nth-child(1) > em:nth-child(1)")).isDisplayed());
		
	}
	
	// END STORY 3 TESTS
	
}
