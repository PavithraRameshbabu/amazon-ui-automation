package UIAutomation;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Amazon {

	public static void main(String[] args) {

		WebDriver driver = new ChromeDriver();
		driver.get("https://www.amazon.in/");

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		WebElement inputValue = driver.findElement(By.id("twotabsearchtextbox"));
		inputValue.sendKeys("lg soundbar");

		WebElement searchButton = driver.findElement(By.id("nav-search-submit-button"));
		searchButton.submit();

		List<WebElement> results = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']"));
		Map<String, Integer> productInfo = new HashMap<>();
		System.out.println("Number of results: " + results.size());

		for (WebElement product : results) {
			WebElement productNameElement = product.findElement(By.className("a-text-normal"));

			List<WebElement> productPriceElements = product.findElements(By.className("a-price-whole"));
			WebElement productPrice;

			if (!productPriceElements.isEmpty()) {
				productPrice = productPriceElements.get(0);

				productInfo.put(productNameElement.getText(),
						Integer.parseInt(productPrice.getText().replace(",", "").trim()));
			} else {
				productInfo.put(productNameElement.getText(), 0);
			}
		}
		List<Map.Entry<String, Integer>> sortedProducts = productInfo.entrySet().stream()
				.sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());

		for (Map.Entry<String, Integer> entry : sortedProducts) {
			System.out.println("Product Name : "+ entry.getKey() + " : Rs." + entry.getValue());
		}

		driver.quit();
	}

}
