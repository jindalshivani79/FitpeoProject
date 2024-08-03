import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Main {

	public static void main(String[] args) throws Exception {

		WebDriver driver = new ChromeDriver(); // Initialize ChromeWebDriver

		driver.manage().window().maximize(); // to maximize the output window

		driver.navigate().to("https://www.fitpeo.com/"); // launching website can also use get(URL) method

		driver.findElement(By.xpath("//a[@href='/revenue-calculator']")).click(); // Navigate to the Revenue Calculator
																					// using Xpath locator

		// Wait until the slider is visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement elementSliderInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='range']")));

		// Move the slider to the desired position using drag and drop
		Actions actions = new Actions(driver);
		actions.moveToElement(elementSliderInput).clickAndHold().moveByOffset(94, 0).release().perform();

		// Wait for the input field to become visible
		WebElement textField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='number']")));

		// Clear input field using Actions
		actions.moveToElement(textField).click().keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
				.sendKeys(Keys.BACK_SPACE).perform();

		// Enter the value 560 in the text field
		textField.sendKeys("560");

		// Trigger the change event to ensure the slider updates
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].dispatchEvent(new Event('change'))", textField);

		// Verify the slider value changes accordingly
		WebElement slider = driver.findElement(By.xpath("//input[@type='range']"));
		String sliderValue = slider.getAttribute("value");
		if (!sliderValue.equals("560")) {
			System.out.println("The slider value is incorrect.Actual: " + sliderValue);
		} else {
			System.out.println("Slider value set successfully to: " + sliderValue);
		}

		// Again clear the input field
		actions.moveToElement(textField).click().keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
				.sendKeys(Keys.BACK_SPACE).perform();

		// Send Input value in the text field
		driver.findElement(By.xpath("//input[@type='number']")).sendKeys("820");

		// Select checkboxes according to the test case
		WebElement checkbox1 = driver.findElement(By.xpath("(//input[@type='checkbox'])[1]"));
		WebElement checkbox2 = driver.findElement(By.xpath("(//input[@type='checkbox'])[2]"));
		WebElement checkbox3 = driver.findElement(By.xpath("(//input[@type='checkbox'])[3]"));
		WebElement checkbox4 = driver.findElement(By.xpath("(//input[@type='checkbox'])[8]"));
		checkbox1.click();
		checkbox2.click();
		checkbox3.click();
		checkbox4.click();

		// Create List of all checkboxes
		List<WebElement> allCheckboxes = driver.findElements(By.xpath("//input[@type='checkbox']"));

		// Count selected checkboxes using enhanced forloop
		int selectedCount = 0;
		for (WebElement checkbox : allCheckboxes) {
			if (checkbox.isSelected()) {
				selectedCount++;
			}
		}

		// Validate the number of selected checkboxes
		int expectedSelectedCount = 4; // Expect 4 checkboxes to be selected
		if (selectedCount == expectedSelectedCount) {
			System.out.println("Validation passed: " + selectedCount + " checkboxes are selected.");
		} else {
			System.out.println("Validation failed");
		}

		Thread.sleep(1000); // wait for seeing the result,optional

		// Close the current browser
		driver.quit();

	}

}
