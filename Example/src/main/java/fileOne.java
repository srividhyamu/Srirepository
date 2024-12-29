
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class fileOne {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.setProperty("webdriver.chrome.driver", "D:\\SrividhyaCoding\\chromedriver-win64\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		// Launching the website
		driver.get("https://elpais.com/");

		// printing the title of the page
		System.out.println("Title is:" + driver.getTitle());

		// accepting the notice information
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		WebElement accept = driver.findElement(By.id("didomi-notice-agree-button"));
		accept.click();

		// Ensuring website text displayed in spanish
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		WebElement language = driver.findElement(By.xpath("//*[@id=\'edition_head\']/a/span"));
		String websiteText = language.getText();
		System.out.println("Website text displayed is: " + websiteText);
		String expectedText = "ESPAÑA";
		Assert.assertEquals(websiteText, expectedText);

		// navigating to Opinion section
		WebElement opinion = driver.findElement(By.xpath("//*[@id=\"csw\"]/div[1]/nav/div/a[2]"));
		opinion.click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		WebElement opinionTitle = driver.findElement(By.xpath("//*[@id='csw']/div/div/h1/a"));
		System.out.println(opinionTitle.getAttribute("title"));

		// Fetching the first five articles & printing the title and content
		for (int i = 0; i < 5; i++) {
			WebElement article = driver.findElements(By.xpath("//div[1]/section/div/article/header/h2")).get(i);
			System.out.println("\n" + (article.getText()));
			WebElement articleContent = driver.findElements(By.xpath("//div[1]/section/div/article/p")).get(i);
			System.out.println("\n" + (articleContent.getText()) + "\n");
		}

		driver.quit();

	}

}
