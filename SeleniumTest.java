package com.browserstack.training.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.util.Assert;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class SeleniumTest {
	
	private static final String USERNAME_PROPERTY = "username";
	private static final String PASSWORD_PROPERTY = "password";
	private static final String PASSED = "passed";
	private static final String FAILED = "failed";
	private static final String COMPLETED = "Completed";
	
	@Test
	public void startAmazonAssignment () {
		WebDriver driver = null;
		
		final String username = System.getProperty(USERNAME_PROPERTY);
		final String password = System.getProperty(PASSWORD_PROPERTY);
		
		if (Objects.isNull(username) || username.isBlank()) {
			throw new AssertionError("Invalid username");
		}
		
		if (Objects.isNull(password) || password.isBlank()) {
			throw new AssertionError("Invalid password");
		}
		
		try {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "latest");
			
			HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
			browserstackOptions.put("os", "Windows");
			browserstackOptions.put("osVersion", "11");
			browserstackOptions.put("projectName", "Amazon Assignment");
			browserstackOptions.put("buildName", "AA 1.0");
			browserstackOptions.put("sessionName", "Amazon iPhone X");
			browserstackOptions.put("local", "false");
			browserstackOptions.put("consoleLogs", "info");
			browserstackOptions.put("networkLogs", "true");
			browserstackOptions.put("seleniumVersion", "4.1.0");
			browserstackOptions.put("telemetryLogs", "true");
			capabilities.setCapability("bstack:options", browserstackOptions);
			
			driver = new RemoteWebDriver(new URL("https://" + username + ":" + password + "@hub-cloud.browserstack.com/wd/hub"),
			                             capabilities);
			
			final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			checkIOSOption(driver, wait);
			
			driver.findElement(By.id("s-result-sort-select_2")).click();
			driver.findElement(By.cssSelector("div.s-main-slot.s-result-list.s-search-results.sg-row"));
			
			driver.findElements(By.className("s-result-item")).stream().sequential().filter(webElement -> {
				
				if (Objects.isNull(webElement)) {
					return Boolean.FALSE;
				}
				final String value = webElement.getAttribute("data-asin");
				return Objects.nonNull(value) && !value.isBlank();
				
			}).forEach(webElement -> {
				
				String link = null;
				String productName = null;
				StringBuilder price = new StringBuilder();
				try {
					WebElement productElement = webElement.findElement(
							By.cssSelector("div.a-section.a-spacing-small.a-spacing-top-small"));
					WebElement linkElement = productElement.findElement(
							By.cssSelector("a.a-link-normal.s-underline-text.s-underline-link-text.s-link-style.a-text-normal"));
					link = linkElement.getAttribute("href");
					productName = linkElement.findElement(By.tagName("span")).getText();
					price.append(productElement.findElement(By.cssSelector("span.a-price-symbol")).getText());
					price.append(productElement.findElement(By.cssSelector("span.a-price-whole")).getText());
					price.append(".");
					price.append(productElement.findElement(By.cssSelector("span.a-price-fraction")).getText());
				} catch (NoSuchElementException e) {
					//e.printStackTrace();
				}
				
				System.out.println("Link = " + link);
				System.out.println("Product Name = " + productName);
				System.out.println("Price = " + (price.isEmpty() ? "NA" : price));
			});
			markTestStatus(PASSED, COMPLETED, driver);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (Objects.nonNull(driver)) {
				driver.quit();
			}
		}
	}
	
	@Test
	public void startBStackDemoAssignment () {
		
		WebDriver driver = null;
		
		final String username = System.getProperty(USERNAME_PROPERTY);
		final String password = System.getProperty(PASSWORD_PROPERTY);
		
		if (Objects.isNull(username) || username.isBlank()) {
			throw new AssertionError("Invalid username");
		}
		
		if (Objects.isNull(password) || password.isBlank()) {
			throw new AssertionError("Invalid password");
		}
		
		try {
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "latest");
			
			HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
			browserstackOptions.put("os", "Windows");
			browserstackOptions.put("osVersion", "11");
			browserstackOptions.put("projectName", "BStack Assignment");
			browserstackOptions.put("buildName", "BSA 1.0");
			browserstackOptions.put("sessionName", "BStack iPhone 12");
			browserstackOptions.put("local", "false");
			browserstackOptions.put("consoleLogs", "info");
			browserstackOptions.put("networkLogs", "true");
			browserstackOptions.put("seleniumVersion", "4.1.0");
			browserstackOptions.put("telemetryLogs", "true");
			capabilities.setCapability("bstack:options", browserstackOptions);
			
			driver = new RemoteWebDriver(new URL("https://" + username + ":" + password + "@hub-cloud.browserstack.com/wd/hub"),
			                             capabilities);
			
			final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			
			driver.get("https://www.bstackdemo.com");
			driver.findElement(By.id("signin")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("react-select-2-input")));
			driver.findElement(By.id("react-select-2-input")).sendKeys("d");
			driver.findElement(By.id("react-select-2-option-0-0")).click();
			driver.findElement(By.id("react-select-3-input")).sendKeys("t");
			driver.findElement(By.id("react-select-3-option-0-0")).click();
			driver.findElement(By.tagName("Button")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("shelf-item")));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("shelf-item__title")));
			
			Optional<WebElement> opWebElement = driver.findElements(By.className("shelf-item")).parallelStream().unordered()
			                                          .filter(webElement -> {
				                                          String itemText = webElement.findElement(
						                                          By.className("shelf-item__title")).getText();
				                                          return "iPhone 12".equalsIgnoreCase(itemText);
			                                          }).findFirst();
			
			if (opWebElement.isEmpty()) {
				System.out.println("Unable to find iPhone 12");
				return;
			}
			opWebElement.get().findElement(By.className("shelf-item__buy-btn")).click();
			driver.findElement(By.className("buy-btn")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("firstNameInput")));
			driver.findElement(By.id("firstNameInput")).sendKeys("Abrar");
			driver.findElement(By.id("lastNameInput")).sendKeys("Mehdi");
			driver.findElement(By.id("addressLine1Input")).sendKeys("Hyderabad");
			driver.findElement(By.id("provinceInput")).sendKeys("Telangana");
			driver.findElement(By.id("postCodeInput")).sendKeys("500032");
			driver.findElement(By.id("checkout-shipping-continue")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("confirmation-message")));
			
			String confirmationText = driver.findElement(By.id("confirmation-message")).getText();
			Assert.hasText("Your Order has been successfully placed.", confirmationText);
			markTestStatus(PASSED, COMPLETED, driver);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (Objects.nonNull(driver)) {
				driver.quit();
			}
		}
	}
	
	private static void markTestStatus (String status, String reason, WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"" + status +
				                  "\", \"reason\": \"" + reason + "\"}}");
	}
	
	@Test
	public void startBrowserstackInception () {
		
		WebDriver driver = null;
		
		final String username = System.getProperty(USERNAME_PROPERTY);
		final String password = System.getProperty(PASSWORD_PROPERTY);

        /*if (Objects.isNull(username) || username.isBlank()) {
            throw new AssertionError("Invalid username");
        }

        if (Objects.isNull(password) || password.isBlank()) {
            throw new AssertionError("Invalid password");
        }*/
		
		try {
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "latest");
			
			HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
			browserstackOptions.put("os", "Windows");
			browserstackOptions.put("osVersion", "11");
			browserstackOptions.put("projectName", "Browserstack Inception Assignment");
			browserstackOptions.put("buildName", "BSIA 1.0");
			browserstackOptions.put("sessionName", "Browserstack Inception");
			browserstackOptions.put("local", "false");
			browserstackOptions.put("consoleLogs", "info");
			browserstackOptions.put("networkLogs", "true");
			browserstackOptions.put("seleniumVersion", "4.1.0");
			browserstackOptions.put("telemetryLogs", "true");
			capabilities.setCapability("bstack:options", browserstackOptions);

            /*driver = new RemoteWebDriver(
                    new URL("https://" + username + ":" + password + "@hub-cloud.browserstack.com/wd/hub"),
                    capabilities);
*/
			driver = new ChromeDriver();
			
			final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			
			driver.get("https://www.browserstack.com");
			driver.findElement(By.cssSelector(".sign-in-link")).click();
			driver.findElement(By.id("user_email_login")).sendKeys("**********");
			driver.findElement(By.id("user_password")).sendKeys("***********");
			driver.findElement(By.id("user_submit")).click();
			
			FluentWait fluentWait = new FluentWait<>(driver);
			fluentWait.withTimeout(Duration.ofSeconds(5));
			driver.findElement(By.xpath("(//div[@data-test-browser=\"chrome\"]//div[@role=\"listitem\"][5])")).click();
			fluentWait.withTimeout(Duration.ofSeconds(30));
			
			Actions action = new Actions(driver).sendKeys(Keys.chord(Keys.CONTROL, "l"));
			action.sendKeys(Keys.chord("Google", Keys.ENTER));
			
			fluentWait.withTimeout(Duration.ofSeconds(5));
			markTestStatus(PASSED, COMPLETED, driver);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (Objects.nonNull(driver)) {
				driver.quit();
			}
		}
	}
	
	private void checkIOSOption (WebDriver driver, WebDriverWait wait) {
		try {
			driver.get("https://www.amazon.in");
			driver.findElement(By.id("twotabsearchtextbox")).sendKeys("iPhone X");
			driver.findElement(By.id("nav-search-submit-button")).click();
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("p_n_operating_system_browse-bin/1485080031")));
			driver.findElement(By.id("p_n_operating_system_browse-bin/1485080031")).click();
			driver.findElement(By.cssSelector("span.a-dropdown-prompt")).click();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			checkIOSOption(driver, wait);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
