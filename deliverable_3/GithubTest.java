package deliverable_3;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GithubTest {

	WebDriver driver;
	
	@Before
	public void setUpDriver() {
		
		FirefoxProfile profile = new FirefoxProfile();
		
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "image/jpeg, application/octet-stream, text/plain");
		profile.setPreference("browser.download.manager.showWhenStarting", false);
		profile.setPreference("browser.download.dir", "/tmp");
		profile.setPreference("browser.download.folderList", 2);
		
		driver = new FirefoxDriver(profile);
	}
	
	@After
	public void tearDownDriver() {
		
		driver.quit();
	}
	
	
	// STORY 1 TESTS
	
	@Test
	/*

	 Given a correct username
	 	and a correct password,
	 When I attempt to log-in with those credentials,
	 Then I should be redirected to my dashboard
 
	 */
	public void testStory1Scenario1() {
		
		driver.get("https://github.com/login");
		
		WebElement loginField = driver.findElement(By.id("login_field"));
		WebElement passwordField = driver.findElement(By.id("password"));
		
		loginField.sendKeys("cs1632test");
		passwordField.sendKeys("1632password");
		
		driver.findElement(By.cssSelector("div.auth-form-body > input[name=\"commit\"]")).click();
		
		assertEquals(driver.getCurrentUrl(), "https://github.com/");
				
	}
	
	@Test
	/*
	
	Given an incorrect username
		and a correct password,
	When I attempt to log-in with those credentials,
	Then I should be notified that my log-in attempt failed.

	 */
	public void testStory1Scenario2() {
		
		driver.get("https://github.com/login");
		
		WebElement loginField = driver.findElement(By.id("login_field"));
		WebElement passwordField = driver.findElement(By.id("password"));
		
		loginField.sendKeys("wrongsdasdasldksa;lda");
		passwordField.sendKeys("1632password");
		
		driver.findElement(By.cssSelector("div.auth-form-body > input[name=\"commit\"]")).click();
		
		assertNotEquals(driver.getCurrentUrl(), "https://github.com/");
				
	}
	
	@Test
	/*
	
	Given a correct username
		and an incorrect password,
	When I attempt to log-in with those credentials,
	Then I should be notified that my log-in attempt failed.

	 */
	public void testStory1Scenario3() {
		
		driver.get("https://github.com/login");
		
		WebElement loginField = driver.findElement(By.id("login_field"));
		WebElement passwordField = driver.findElement(By.id("password"));
		
		loginField.sendKeys("cs1632test");
		passwordField.sendKeys("wrongpassword");
		
		driver.findElement(By.cssSelector("div.auth-form-body > input[name=\"commit\"]")).click();
		
		assertNotEquals(driver.getCurrentUrl(), "https://github.com/");
		
		
	}
	
	@Test
	/*
	
	Given an incorrect username
		and an incorrect password,
	When I attempt to log-in with those credentials,
	Then I should be notified that my log-in attempt failed.

	 */
	public void testStory1Scenario4() {
		
		driver.get("https://github.com/login");
		
		WebElement loginField = driver.findElement(By.id("login_field"));
		WebElement passwordField = driver.findElement(By.id("password"));
		
		loginField.sendKeys("wronguseadasasdr");
		passwordField.sendKeys("wrongpassword");
		
		driver.findElement(By.cssSelector("div.auth-form-body > input[name=\"commit\"]")).click();
		
		assertNotEquals(driver.getCurrentUrl(), "https://github.com/");
		
	}
	
	@Test
	/*

	Given a correct username but with some letters capitalized
		and an incorrect password,
	When I attempt to log-in with those credentials,
	Then I should be redirected to my dashboard.

	 */
	public void testStory1Scenario5() {
		
		driver.get("https://github.com/login");
		
		WebElement loginField = driver.findElement(By.id("login_field"));
		WebElement passwordField = driver.findElement(By.id("password"));
		
		loginField.sendKeys("cs1632TeSt");
		passwordField.sendKeys("1632password");
		
		driver.findElement(By.cssSelector("div.auth-form-body > input[name=\"commit\"]")).click();
		
		assertEquals(driver.getCurrentUrl(), "https://github.com/");
		
	}
	
	// END STORY 1 TESTS
	
	// STORY 2 TESTS
	
	@Test
	/*

	Given a query string that is empty (blank),
	When I attempt to search public repositories with that query string,
	Then I should be redirected to the search page.

	 */
	public void testStory2Scenario1() {
		
		driver.get("https://github.com/");
		
		WebElement searchBar = driver.findElement(By.cssSelector(".js-site-search-focus"));
		
		searchBar.sendKeys("");
		searchBar.submit();
		
		assertTrue(driver.getCurrentUrl().indexOf("https://github.com/search") != -1);
	}
	
	@Test
	/*

	Given a query string that is very long, arcane, and meaningless,
	When I attempt to search public repositories using that query string,
	Then I should be redirected to a page that indicates that no results were found.

	 */
	public void testStory2Scenario2() {
		
		driver.get("https://github.com/");
		
		WebElement searchBar = driver.findElement(By.cssSelector(".js-site-search-focus"));
		
		searchBar.sendKeys("askdjsalfhakjsdfhdskjfhdkhfdkajfhsdkjfhsdjkhfsd");
		searchBar.submit();
		
		try {
			
			driver.findElement(By.xpath("//*[contains(text(), 'We couldn')]"));
			
		} catch (NoSuchElementException e) {
			
			fail();
		}

	}
	
	@Test
	/*

	Given a query string that contains a keyword of a known repository,
	When I attempt to search public repositories using that query string,
	Then I should be redirected to a results page listing the known repository.

	 */
	public void testStory2Scenario3() {
		
		driver.get("https://github.com/");
		
		WebElement searchBar = driver.findElement(By.cssSelector(".js-site-search-focus"));
		
		searchBar.sendKeys("Eurysta");
		searchBar.submit();
		
		try {
			
			driver.findElement(By.linkText("cstavish/Eurysta"));
			
		} catch (NoSuchElementException e) {
			
			fail();
		}	
	}
	
	@Test
	/*

	Given a keyword of a known repository,
		and an identifier of the language in which most of the code in that repository is written,
	When I attempt to search public repositories using the query string formed by concatenating 
		the keyword with "language:" and the language identifier,
	Then I should be redirected to a results page listing the known repository.

	 */
	public void testStory2Scenario4() {
		
		driver.get("https://github.com/");
		
		WebElement searchBar = driver.findElement(By.cssSelector(".js-site-search-focus"));
		
		searchBar.sendKeys("Eurysta language:C");
		searchBar.submit();
		
		try {
			
			driver.findElement(By.linkText("cstavish/Eurysta"));
			
		} catch (NoSuchElementException e) {
			
			fail();
		}
		
	}
	
	// END STORY 2 TESTS
	
	// STORY 4 TESTS
	
	@Test
	/*

	Given a repository,
		and a plain text file inside that repository,
	When I attempt to view the "raw" version of the file,
	Then the file should be downloaded successfully.
	
	*/
	public void testStory4Scenario1() {
		
		driver.get("https://github.com/cs1632test/deliverable3_test/");
		
		driver.findElement(By.linkText("a_text_file.txt")).click();
		
		driver.findElement(By.id("raw-url")).click();
		
		try {
			
			driver.findElement(By.xpath("//*[contains(text(), 'Not found')]"));
			fail();
			
		} catch (NoSuchElementException e) {
			
			assertTrue(true);
		}
		
	}
	
	@Test
	/*

	Given a repository,
		and a jpeg image file inside that repository,
	When I attempt to view the "raw" version of the file,
	Then the file should be downloaded successfully.
	
	*/
	public void testStory4Scenario2() {
		
		driver.get("https://github.com/cs1632test/deliverable3_test/");
		
		driver.findElement(By.linkText("histology.jpeg")).click();
		
		driver.findElement(By.id("raw-url")).click();
		
		try {
			
			driver.findElement(By.xpath("//*[contains(text(), 'Not found')]"));
			fail();
			
		} catch (NoSuchElementException e) {
			
			assertTrue(true);
		}
		
	}
	
	@Test
	/*

	Given a repository,
		and an html file,
	When I attempt to view the "raw" version of the file,
	Then the file should be downloaded successfully.

	*/
	public void testStory4Scenario3() {
		
		driver.get("https://github.com/cs1632test/deliverable3_test/");
		
		driver.findElement(By.linkText("4.4.20.html")).click();
		
		driver.findElement(By.id("raw-url")).click();
		
		try {
			
			driver.findElement(By.xpath("//*[contains(text(), 'Not found')]"));
			fail();
			
		} catch (NoSuchElementException e) {
			
			assertTrue(true);
		}
		
	}
	
	@Test
	/*

	Given a repository,
		and a pdf file inside that repository,
	When I attempt to view the "raw" version of the file,
	Then the file should be downloaded successfully.

	*/
	public void testStory4Scenario4() {

		driver.get("https://github.com/cs1632test/deliverable3_test/");
		
		driver.findElement(By.linkText("xmltutorial.pdf")).click();
		
		driver.findElement(By.id("raw-url")).click();
		
		try {
			
			driver.findElement(By.xpath("//*[contains(text(), 'Not found')]"));
			fail();
			
		} catch (NoSuchElementException e) {
			
			assertTrue(true);
		}
		
		
	}
	
	@Test
	/*

	Given a repository,
		and a tar.gz file inside that repository,
	When I attempt to view the "raw" version of the file,
	Then the file should be downloaded successfully.

	*/
	public void testStory4Scenario5() {
		
		driver.get("https://github.com/cs1632test/deliverable3_test/");
		
		driver.findElement(By.linkText("archive.tar.gz")).click();
		
		driver.findElement(By.id("raw-url")).click();
		
		try {
			
			driver.findElement(By.xpath("//*[contains(text(), 'Not found')]"));
			fail();
			
		} catch (NoSuchElementException e) {
			
			assertTrue(true);
		}
		
	}
	
	// END STORY 4 TESTS
	
}
