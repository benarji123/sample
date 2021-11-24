package CommonFunLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.PropertyFileUtil;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FunctionsLibrary {
	public static WebDriver driver;
	//start browser
	public static WebDriver startBrowser() throws Throwable {
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Chrome")) {
			WebDriverManager.chromedriver().driverVersion("95.0.4638.69").setup();
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			
		}else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
		}else {
			System.out.println("this browser not exested");
		}
		return driver;
	}
	// launch url
	public static void openApplication(WebDriver driver) throws Throwable {

		driver.get(PropertyFileUtil.getValueForKey("Url"));
		
	}
	// wait for element
	public static void waitForElement(WebDriver driver,String LocatorType,String LocatorValue,String TestData) {
		WebDriverWait wait =new WebDriverWait(driver, Integer.parseInt(TestData));
		if(LocatorType.equalsIgnoreCase("name")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
			
		}else if(LocatorType.equalsIgnoreCase("id")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)) );
		}else if(LocatorType.equalsIgnoreCase("xpath")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
			
			
	}else {
		System.out.println("unable to perfome wait for element method");
	}

} 
	public static void typeAction(WebDriver driver,String locatortype,String locatorvalue,String testdata) {
		if(locatortype.equalsIgnoreCase("name")) {
			driver.findElement(By.name(locatorvalue)).clear();
			driver.findElement(By.name(locatorvalue)).sendKeys(testdata);
		}else if(locatortype.equalsIgnoreCase("id")) {
			driver.findElement(By.id(locatorvalue)).clear();
			driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
		}else if(locatortype.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(locatorvalue)).clear();
			driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);
		}
	}
	public static void clickAction(WebDriver driver,String locatortype,String locatorvalue) {
		if(locatortype.equalsIgnoreCase("name")) {
			driver.findElement(By.name(locatorvalue)).click();
		}else if(locatortype.equalsIgnoreCase("id")) {
			driver.findElement(By.id(locatorvalue)).click();
		}else if(locatortype.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(locatorvalue)).click();
		}else {
			System.out.println("unble to perfome click action");
		}
	}public static void validateTitle(WebDriver driver,String testdata) {
		String titleofpage=driver.getTitle();
		try {
		Assert.assertEquals(titleofpage, testdata,"Both titles not matched");
		}catch (Throwable e) {
			System.out.println(e.getMessage());
		}
		
	}
	public static void closeBrowser(WebDriver driver) {
		//driver.close();
		driver.quit();
	}
	//capture data
	public static void captureData(WebDriver driver,String locatortype,String locatorvalue) throws Throwable {
		String Capturedata=" ";
		if(locatortype.equalsIgnoreCase("name")) {
			Capturedata=driver.findElement(By.name(locatorvalue)).getAttribute("value");
		}else if(locatortype.equalsIgnoreCase("id")) {
			Capturedata=driver.findElement(By.id(locatorvalue)).getAttribute("value");
			
		}else if(locatortype.equalsIgnoreCase("xpath")) {
			Capturedata=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
		}
		FileWriter fw=new FileWriter("./CaptureData/supplier.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(Capturedata);
		bw.flush();
		bw.close();
		
	}
	public static void validateSalesnumber(WebDriver driver,String colnum) throws Throwable  {
		FileReader fr=new FileReader("./CaptureData/supplier.txt");
		BufferedReader br=new BufferedReader(fr);
		String salesnum=br.readLine();
		int columnnum=Integer.parseInt(colnum);
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).isDisplayed()) ;
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searh-panel"))).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).clear();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).sendKeys(salesnum);
		Thread.sleep(5000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		WebElement element=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("table")));
		List<WebElement>rows=element.findElements(By.tagName("tr"));
		for(int i=1;i<=rows.size();i++){
			String supnum=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+columnnum+"]/div/span/span")).getText();
			Assert.assertEquals(salesnum, supnum,"supplier number not matched");
			System.out.println(salesnum+"   "+supnum);
			break;
			
		}
			
			
		
		
	}
	
	public static void mouseClick(WebDriver driver) throws Throwable {

		Actions act=new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//*[@id=\"mi_a_stock_items\"]/a"))).build().perform();
		Thread.sleep(5000);
		act.moveToElement(driver.findElement(By.xpath("//*[@id=\"mi_a_stock_categories\"]/a"))).click().build().perform();
		
	}
	public static void StockTable(WebDriver driver,String categoryname) throws Throwable {
		String testdata=categoryname;
		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).isDisplayed());
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searh-panel"))).click();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).sendKeys(testdata);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		WebElement element=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("stock-categorytable")));
		List<WebElement>rows=element.findElements(By.tagName("tr"));
		for(int i=1;i<=rows.size();i++) {
			String tablecatename=driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr["+i+"]/td[4]/div/span/span")).getText();
			Assert.assertEquals(testdata, tablecatename,"both are not equal");
			System.out.println(testdata+"   "+tablecatename);
			break;
		}
	}
	public static void captureData1(WebDriver driver,String locatortype,String locatorvalue)throws Throwable {
		String capture_salesnum="";
		if(locatortype.equalsIgnoreCase("name")) {
			capture_salesnum=driver.findElement(By.name(locatorvalue)).getAttribute("value");
		}else if(locatortype.equalsIgnoreCase("id")) {
			capture_salesnum=driver.findElement(By.id(locatorvalue)).getAttribute("value");
		}else if(locatortype.equalsIgnoreCase("xpath")) {
			capture_salesnum=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
		}
		FileWriter fw=new FileWriter("./CaptureData/salesnum.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(capture_salesnum);
		bw.flush();
		bw.close();
	
	}
	public static void dropDown(WebDriver driver,String locatortype,String locatorvalue,String custermeid)throws Throwable {
		if(locatortype.equalsIgnoreCase("name")) {
			Select s=new Select(driver.findElement(By.name(locatorvalue)));
			Thread.sleep(5000);
			s.selectByVisibleText(custermeid);
			
		}if(locatortype.equalsIgnoreCase("id")) {
			Select s=new Select(driver.findElement(By.id(locatorvalue)));
			Thread.sleep(5000);
			s.selectByVisibleText(custermeid);
			
			
		}if(locatortype.equalsIgnoreCase("xpath")) {
			Select s=new Select(driver.findElement(By.xpath(locatorvalue)));
			Thread.sleep(5000);
			s.selectByVisibleText(custermeid);
			
		}
			
		}
		
	

	public static void verifySalesTable(WebDriver driver,String testdata)throws Throwable {
		FileReader fr=new FileReader("./CaptureData/salesnum.txt");
		BufferedReader br=new BufferedReader(fr);
		String salesnum=br.readLine();
		int colnum=Integer.parseInt(testdata);

		if(!driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).isDisplayed());
		Thread.sleep(5000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("searh-panel"))).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-box"))).sendKeys(salesnum);
		Thread.sleep(5000);
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search-button"))).click();
		WebElement el=driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("sales_table")));
		List<WebElement>rows=el.findElements(By.xpath("//table[@id='tbl_a_saleslist']/tbody/tr"));
		for(int i=1;i<=rows.size();i++) {
			String salenum=driver.findElement(By.xpath("//table[@id='tbl_a_saleslist']/tbody/tr["+i+"]/td["+colnum+"]/div/span/span")).getText();
			Assert.assertEquals(salesnum, salenum,"not matched both sales number");
			break;
		}
	}
	public static void selectDate(WebDriver driver,String locatorvalue,String date) {
		((JavascriptExecutor)driver).executeScript("document.getElementById('"+locatorvalue+"').value='"+date+"'");
		
	}
	public static String geneteDate() {
		Date date=new Date();
		DateFormat df=new SimpleDateFormat("YYYY-MM-dd hh-mm-ss");
		return df.format(date);
		
	}
	public static void actionClick(WebDriver driver,String locatortype,String locatorvalue) {
		if(locatortype.equalsIgnoreCase("xpath")){
		WebElement element = driver.findElement(By.xpath(locatorvalue));
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().build().perform();
		}
		 else if(locatortype.equalsIgnoreCase("id")) {
			 WebElement element = driver.findElement(By.id(locatorvalue));
			 Actions actions = new Actions(driver);
			 actions.moveToElement(element).click().build().perform();

		}else if(locatortype.equalsIgnoreCase("name")) {
			WebElement element = driver.findElement(By.name(locatorvalue));
			Actions actions = new Actions(driver);
			actions.moveToElement(element).click().build().perform();

		}

	}
 }

