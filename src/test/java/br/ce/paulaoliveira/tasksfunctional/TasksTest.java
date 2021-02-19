package br.ce.paulaoliveira.tasksfunctional;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


public class TasksTest {
	
	@BeforeClass
	public static void setup() {
		System.setProperty("webdriver.chrome.driver","/home/paula/seleniumDrivers/chromedriver");
	}
	
	private WebDriver acessarAplicacao() {
		ChromeOptions chromeOptions =  new ChromeOptions();
		chromeOptions.addArguments("--headless","--no-sandbox");
		WebDriver driver = new ChromeDriver(chromeOptions);
		
		driver.navigate().to("http:/localhost:8080/tasks");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
	
	
	@Test
	public void deveSalvarTarefaComSucesso() {
		WebDriver driver = acessarAplicacao();
		try {
			driver.navigate().to("http:/localhost:8080/tasks");
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
	public void naodeveSalvarTarefaSemDescricao() {
		WebDriver driver = acessarAplicacao();
		try {
			driver.navigate().to("http:/localhost:8080/tasks");
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
	public void naoDeveSalvarTarefaSemData() {
		WebDriver driver = acessarAplicacao();
		try {
			driver.navigate().to("http:/localhost:8080/tasks");
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
	public void naoDeveSalvarTarefaComDataPassada() {
		WebDriver driver = acessarAplicacao();
		try {
			driver.navigate().to("http:/localhost:8080/tasks");
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
	
	
}

