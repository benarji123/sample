package DriverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.FunctionsLibrary;
import Utilities.ExcelFileUtil;

public class DriverScript {
WebDriver driver;

String excelinputpath="D:\\desktop data\\eclipse-workspace\\StockAccounting\\TestInput\\HybridTest.xlsx";
String exceloutputpath="D:\\desktop data\\eclipse-workspace\\StockAccounting\\TestOutput\\excelresult.xlsx";
ExtentReports report;
ExtentTest logger;
public void startTest()throws Throwable {
ExcelFileUtil excel=new ExcelFileUtil(excelinputpath);
for(int i=1;i<=excel.rowCount("MasterTestCases");i++) {
	String moduleStatus="";
	if(excel.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y")) {
		String tcmodulename=excel.getCellData("MasterTestCases", i, 1);
		report=new ExtentReports("./Extentsreports/"+tcmodulename+FunctionsLibrary.geneteDate()+".html");
		logger=report.startTest(tcmodulename);
		for(int j=1;j<=excel.rowCount(tcmodulename);j++) {
			String Description=excel.getCellData(tcmodulename, j, 0);
			String FunctionName=excel.getCellData(tcmodulename, j, 1);
			String LocatorType=excel.getCellData(tcmodulename, j, 2);
			String LocatorValue=excel.getCellData(tcmodulename, j, 3);
			String TestData=excel.getCellData(tcmodulename, j, 4);
			try{
				if(FunctionName.equalsIgnoreCase("startBrowser")) {
					driver=FunctionsLibrary.startBrowser();
					logger.log(LogStatus.INFO,Description );
				}else if(FunctionName.equalsIgnoreCase("openApplication")){
					FunctionsLibrary.openApplication(driver);
					logger.log(LogStatus.INFO,Description );
					
				}else if(FunctionName.equalsIgnoreCase("waitForElement")) {
					FunctionsLibrary.waitForElement(driver, LocatorType, LocatorValue, TestData);
					logger.log(LogStatus.INFO,Description );
				}else if(FunctionName.equalsIgnoreCase("typeActon")) {
					FunctionsLibrary.typeAction(driver, LocatorType, LocatorValue, TestData);
					logger.log(LogStatus.INFO,Description );
				}else if(FunctionName.equalsIgnoreCase("clickActon")) {
					FunctionsLibrary.actionClick(driver, LocatorType, LocatorValue);
					logger.log(LogStatus.INFO,Description );
					
				}else if(FunctionName.equalsIgnoreCase("validateTitle")) {
					FunctionsLibrary.validateTitle(driver, TestData);
					logger.log(LogStatus.INFO,Description );
				}else if(FunctionName.equalsIgnoreCase("closeBrowser")) {
					FunctionsLibrary.closeBrowser(driver);
					
					logger.log(LogStatus.INFO,Description );
				}else if(FunctionName.equalsIgnoreCase("captureData")) {
					FunctionsLibrary.captureData(driver, LocatorType, LocatorValue);
					logger.log(LogStatus.INFO,Description );
				}else if(FunctionName.equalsIgnoreCase("validateSalesnumber")) {
					FunctionsLibrary.validateSalesnumber(driver, TestData);
					logger.log(LogStatus.INFO,Description );
					
				}else if(FunctionName.equalsIgnoreCase("mouseClick")) {
					FunctionsLibrary.mouseClick(driver);
					logger.log(LogStatus.INFO,Description );
				}else if(FunctionName.equalsIgnoreCase("StockTable")) {
					FunctionsLibrary.StockTable(driver, TestData);
					logger.log(LogStatus.INFO,Description );
					
				}else if(FunctionName.equalsIgnoreCase("captureData1")) {
					FunctionsLibrary.captureData1(driver, LocatorType, LocatorValue);
					logger.log(LogStatus.INFO,Description );
				} else if(FunctionName.equalsIgnoreCase("dropDown")) {
					FunctionsLibrary.dropDown(driver, LocatorType, LocatorValue, TestData);
					logger.log(LogStatus.INFO,Description );
					
				}else if(FunctionName.equalsIgnoreCase("verifySalesTable")) {
					FunctionsLibrary.verifySalesTable(driver, TestData);
					logger.log(LogStatus.INFO,Description );
					
				}else if(FunctionName.equalsIgnoreCase("selectDate")) {
					FunctionsLibrary.selectDate(driver, LocatorValue, "2021/05/28");
					logger.log(LogStatus.INFO,Description );
				}
				excel.setCellData(tcmodulename, j, 5, "Pass", "exceloutputpath");
				logger.log(LogStatus.PASS,Description );
				moduleStatus="True";
				
			}catch (Throwable e) {
				System.out.println(e.getMessage());
				excel.setCellData(tcmodulename, j, 5, "Fail", "exceloutputpath");
				logger.log(LogStatus.FAIL, Description);
				moduleStatus="Fail";
				File srcnht=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(srcnht,new File( "./screenshots/"+Description+"_"+FunctionsLibrary.geneteDate()+".png"));
	           String image= logger.addScreenCapture("./screenshots/"+Description+"_"+FunctionsLibrary.geneteDate()+".png");
	           logger.log(LogStatus.FAIL, image);
				
			}
			
		}
		if(moduleStatus.equalsIgnoreCase("True")) {
			excel.setCellData("MasterTestCases", i, 3, "Pass","exceloutputpath" );
		}
		if(moduleStatus.equalsIgnoreCase("Fail")) {
			excel.setCellData("MasterTestCases", i, 3, "Fail","exceloutputpath" );
		}
		
	}else {
		excel.setCellData("MasterTestCases", i, 3, "Blocked", "exceloutputpath");
	}
	report.endTest(logger);
	report.flush();
	
}






}

}
