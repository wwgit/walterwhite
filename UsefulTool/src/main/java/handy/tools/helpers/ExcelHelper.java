package handy.tools.helpers;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public abstract class ExcelHelper extends BasicHelper {
	
	public static boolean isXls(String excelPath) {
		
		System.out.println(excelPath);
		if(excelPath.endsWith(TXTFILE_SUFFIX_XLS_STR)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isXlsx(String excelPath) {
		
		
		if(excelPath.endsWith(TXTFILE_SUFFIX_XLSX_STR)) {
			return true;
		}
		
		return false;
	}
	
	public static int xlsOrXlsx(String excelPath) {
		
		if(excelPath.endsWith(TXTFILE_SUFFIX_XLSX_STR)) {
			return TXTFILE_SUFFIX_XLSX;
		} else if(excelPath.endsWith(TXTFILE_SUFFIX_XLS_STR)) {
			return TXTFILE_SUFFIX_XLS;
		} else {
			return TXTFILE_SUFFIX_UNKNOWN;
		}
		
	}
	
	public static HSSFWorkbook getXlsWorkBook(String excelPath) {
		
		InputStream input = null;
		
		POIFSFileSystem pfs = null;
		HSSFWorkbook workBook = null;
		
		if(false == isXls(excelPath)) return null;
		
		try {
			
			input = new FileInputStream(PathHelper.GetAbsoluteFilePath(excelPath));
			pfs = new POIFSFileSystem(input);
			workBook = new HSSFWorkbook(pfs);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return workBook;
	}
	
	public static void readXlsWorkSheet(HSSFWorkbook workBook) {
		
		HSSFSheet sheet = workBook.getSheetAt(0);
		
		Iterator rowsIt = sheet.rowIterator();
		
		while(rowsIt.hasNext()) {
			HSSFRow row = (HSSFRow) rowsIt.next();
			System.out.println("last column of this row: " + row.getLastCellNum());
			Iterator cells = row.cellIterator();
			while(cells.hasNext()) {
				HSSFCell cell = (HSSFCell) cells.next(); 
				String cellData = cell.getStringCellValue();
				System.out.println(cellData);
			}
		}
		
	}
	
	

}
