
import java.time.Duration;

//import org.json.JSONObject;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

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

		for (int i = 0; i < 5; i++) {
			WebElement article = driver.findElements(By.xpath("//div[1]/section/div/article/header/h2")).get(i);
			String[] title = new String[5];
			title[i] = driver.findElements(By.xpath("//div[1]/section/div/article/header/h2")).get(i).getText();

			// Rapid Translate Multi Traduction API to translate the title
			RestAssured.baseURI = "https://rapid-translate-multi-traduction.p.rapidapi.com/t";
			RequestSpecification request = RestAssured.given();
			request.header("Content-Type", "application/json");
			request.header("x-rapidapi-key", "c98554e261mshfcc7e6f42a282b4p19325djsn272e016cbde8");
			request.header("\"x-rapidapi-host", "rapid-translate-multi-traduction.p.rapidapi.com");
			JSONObject requestParams = new JSONObject();
			requestParams.put("from", "es");
			requestParams.put("to", "en");
			requestParams.put("q", title[i]);
			request.body(requestParams.toJSONString());
			Response response = request.post();
			// System.out.println("Status code is: "+response.statusCode());
			// System.out.println("Status line is: "+response.statusLine());
			ResponseBody body = response.getBody();
			// System.out.println("Response Body is: "+body.asString());
			String translatedHeader = body.asString();
			int length = translatedHeader.length();
			// translatedHeader.substring(2,length-2);
			System.out.println("Translated header is " + translatedHeader.substring(2, length - 2));

		}
		driver.quit();

	}

}
