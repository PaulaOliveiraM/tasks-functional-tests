package br.ce.paulaoliveira.tasksprod;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HealthCheckIT {
	
	@Test
	public void HealthCheck() throws MalformedURLException {
		ChromeOptions chromeOptions =  new ChromeOptions();
	    chromeOptions.addArguments("--headless","--no-sandbox");		
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.1.69:4444/wd/hub"),chromeOptions);
		
		try {
			driver.navigate().to("http://192.168.1.69:9999/tasks/");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			String version = driver.findElement(By.id("version")).getText();
			Assert.assertTrue(version.startsWith("build"));
		}finally {
			driver.quit();
		}		
	}

}
