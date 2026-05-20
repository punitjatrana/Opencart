package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders  {

	// DataProvider 1
	
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException {

	    String path = ".\\testData\\Opencart_LoginData.xlsx";

	    int totalrows = ExcelUtility.getRowCount(path, "Sheet1");
	    int totalcols = ExcelUtility.getCellCount(path, "Sheet1", 1);

	    String logindata[][] = new String[totalrows][totalcols];

	    for(int i = 1; i <= totalrows; i++) {
	        for(int j = 0; j < totalcols; j++) {
	            logindata[i-1][j] = ExcelUtility.getCellData(path, "Sheet1", i, j);
	        }
	    }

	    return logindata;
	}
	
	// DataProvider 2
	
	// DataProvider 3
	
}
