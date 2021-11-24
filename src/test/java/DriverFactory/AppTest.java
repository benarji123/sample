package DriverFactory;

import org.testng.annotations.Test;

public class AppTest {
@Test
public void kistStart() {
	
	DriverScript ds=new DriverScript();
	try {
		ds.startTest();
	} catch (Throwable e) {
		// TODO Auto-generated catch block
		System.out.println(e.getMessage());
	}
}

}
