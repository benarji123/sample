package Utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileUtil {

	public static String getValueForKey(String key)  throws Throwable{
		Properties po=new Properties();
		
	po.load(new FileInputStream("D:\\desktop data\\eclipse-workspace\\StockAccounting\\PropertyFile\\Enveronment.properties"));
	return po.getProperty(key);
	}
	
}
