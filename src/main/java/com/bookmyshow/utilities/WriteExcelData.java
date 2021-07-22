package com.bookmyshow.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcelData {

	public void writeListData(String filename, String sheetname, List<String> data) throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet worksheet = workbook.createSheet(sheetname);
		for (int i = 0; i < data.size(); i++) {
			Row dataRow = worksheet.createRow(i);
			dataRow.createCell(0).setCellValue(data.get(i));
		}
		FileOutputStream out = new FileOutputStream(new File(System.getProperty("user.dir") + "\\Outputs\\ExcelFiles\\" +filename+".xlsx"));
		workbook.write(out);
		out.close();
		workbook.close();
	}

}
