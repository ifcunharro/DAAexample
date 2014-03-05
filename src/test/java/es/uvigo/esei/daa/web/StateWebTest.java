package es.uvigo.esei.daa.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
//puesto por mi este chrome
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import es.uvigo.esei.daa.TestStateUtils;

public class StateWebTest {
	private static final int DEFAULT_WAIT_TIME = 1;
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		TestStateUtils.createFakeContext();
	}
	
	@Before
	public void setUp() throws Exception {
		TestStateUtils.initTestDatabase();
		
		final String baseUrl = "http://localhost:8080/DAAExample/";
		
		driver = new ChromeDriver();
		driver.get(baseUrl);
		driver.manage().addCookie(new Cookie("token", "bXJqYXRvOm1yamF0bw=="));
		
		// Driver will wait DEFAULT_WAIT_TIME if it doesn't find and element.
		driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_TIME, TimeUnit.SECONDS);
		
		driver.get(baseUrl + "main.html");
		driver.findElement(By.id("state-list"));
	}
	
	@After
	public void tearDown() throws Exception {
		TestStateUtils.clearTestDatabase();
		
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@Test
	public void testList() throws Exception {
		verifyXpathCount("//tr", 4);
	}

	@Test
	public void testAdd() throws Exception {
		final String street = "Hola";
		final String number = "60";
		final String locality = "Mundo";
		final String province = "Iago";
		
		driver.findElement(By.name("street")).clear();
		driver.findElement(By.name("street")).sendKeys(street);
		driver.findElement(By.name("number")).clear();
		driver.findElement(By.name("number")).sendKeys(number);
		driver.findElement(By.name("locality")).clear();
		driver.findElement(By.name("locality")).sendKeys(locality);
		driver.findElement(By.name("province")).clear();
		driver.findElement(By.name("province")).sendKeys(province);
		driver.findElement(By.id("btnSubmit")).click();
		driver.findElement(By.xpath("//td[text()='Hola']"));
		
		assertEquals(street, 
			driver.findElement(By.cssSelector("tr:last-child > td.street")).getText()
		);
		assertEquals(number, 
			driver.findElement(By.cssSelector("tr:last-child > td.number")).getText()
		);
		assertEquals(locality, 
				driver.findElement(By.cssSelector("tr:last-child > td.locality")).getText()
		);
		assertEquals(province, 
				driver.findElement(By.cssSelector("tr:last-child > td.province")).getText()
		);
	}

	@Test
	public void testEdit() throws Exception {
		final String street = "Hola";
		final String number = "60";
		final String locality = "Mundo";
		final String province = "Iago";
		
		final String trId = driver.findElement(By.xpath("//tr[last()]")).getAttribute("id");
		driver.findElement(By.xpath("//tr[@id='" + trId + "']//a[text()='Edit']")).click();
		driver.findElement(By.name("street")).clear();
		driver.findElement(By.name("street")).sendKeys(street);
		driver.findElement(By.name("number")).clear();
		driver.findElement(By.name("number")).sendKeys(number);
		driver.findElement(By.name("locality")).clear();
		driver.findElement(By.name("locality")).sendKeys(locality);
		driver.findElement(By.name("province")).clear();
		driver.findElement(By.name("province")).sendKeys(province);
		driver.findElement(By.id("btnSubmit")).click();
		waitForTextInElement(By.name("street"), "");
		waitForTextInElement(By.name("number"), "");
		waitForTextInElement(By.name("locality"), "");
		waitForTextInElement(By.name("province"), "");
		
		assertEquals(street, 
			driver.findElement(By.xpath("//tr[@id='" + trId + "']/td[@class='street']")).getText()
		);
		assertEquals(number, 
			driver.findElement(By.xpath("//tr[@id='" + trId + "']/td[@class='number']")).getText()
		);
		assertEquals(locality, 
				driver.findElement(By.xpath("//tr[@id='" + trId + "']/td[@class='locality']")).getText()
		);
		assertEquals(province, 
				driver.findElement(By.xpath("//tr[@id='" + trId + "']/td[@class='province']")).getText()
		);
		
	}

	@Test
	public void testDelete() throws Exception {
		final String trId = driver.findElement(By.xpath("//tr[last()]")).getAttribute("id");
		driver.findElement(By.xpath("(//a[contains(text(),'Delete')])[last()]")).click();
		driver.switchTo().alert().accept();
		waitUntilNotFindElement(By.id(trId));
	}
	
	private boolean waitUntilNotFindElement(By by) {
	    return new WebDriverWait(driver, DEFAULT_WAIT_TIME)
	    	.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}
	
	private boolean waitForTextInElement(By by, String text) {
	    return new WebDriverWait(driver, DEFAULT_WAIT_TIME)
	    	.until(ExpectedConditions.textToBePresentInElementLocated(by, text));
	}

	private void verifyXpathCount(String xpath, int count) {
		try {
			assertEquals(count, driver.findElements(By.xpath(xpath)).size());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
	}
}
