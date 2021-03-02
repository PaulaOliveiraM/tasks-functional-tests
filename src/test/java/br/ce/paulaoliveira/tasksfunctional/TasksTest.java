package br.ce.paulaoliveira.tasksfunctional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;	
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;


public class TasksTest {
	
	@BeforeClass
	public static void setup() {
		System.setProperty("webdriver.chrome.driver","/home/paula/seleniumDrivers/chromedriver");
	}
	
	private WebDriver acessarAplicacao() throws MalformedURLException {
		ChromeOptions chromeOptions =  new ChromeOptions();
	    chromeOptions.addArguments("--headless","--no-sandbox");
		
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.1.69:4444/wd/hub"),chromeOptions);
		driver.navigate().to("http://192.168.1.69:8080/tasks/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	
	@Test
	public void deveSalvarTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.navigate().to("http:/192.168.1.69:8080/tasks");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			driver.findElement(By.id("saveButton")).click();		
			String message = driver.findElement(By.id("message")).getText();
			
			Assert.assertEquals("Success!", message);
		}finally {
			driver.quit();
		}
	}
	
	@Test
	public void naodeveSalvarTarefaSemDescricao() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.navigate().to("http:/192.168.1.69:8080/tasks");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
			driver.findElement(By.id("saveButton")).click();		
			String message = driver.findElement(By.id("message")).getText();
			
			Assert.assertEquals("Fill the task description", message);
		}finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.navigate().to("http:/192.168.1.69:8080/tasks");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("saveButton")).click();		
			String message = driver.findElement(By.id("message")).getText();
			
			Assert.assertEquals("Fill the due date", message);
		}finally {
			driver.quit();
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.navigate().to("http:/192.168.1.69:8080/tasks");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2011");
			driver.findElement(By.id("saveButton")).click();		
			String message = driver.findElement(By.id("message")).getText();
			
			Assert.assertEquals("Due date must not be in past", message);
		}finally {
			driver.quit();
		}
	}
	
	@Test
	public void deveRemoverTarefaComSucesso() throws MalformedURLException {
		WebDriver driver = acessarAplicacao();
		try {
			driver.navigate().to("http:/192.168.1.69:8080/tasks");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.id("addTodo")).click();
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2050");
			driver.findElement(By.id("saveButton")).click();		
			String message = driver.findElement(By.id("message")).getText();			
			Assert.assertEquals("Success!", message);
			
			driver.findElement(By.xpath("//a[@class='btn btn-outline-danger btn-sm']")).click();
			String message1 = driver.findElement(By.id("message")).getText();			
			Assert.assertEquals("Success!", message1);
			
		}finally {
			driver.quit();
		}
	}
	
}

