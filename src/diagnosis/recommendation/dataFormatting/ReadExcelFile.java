package diagnosis.recommendation.dataFormatting;


import java.io.File;
import java.io.IOException;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReadExcelFile {

	private String inputFile;

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public String decodeICD9Diagnosis(String code) throws IOException  {
		
		this.setInputFile("C:/KID 2009/Single_Level_CCS_2011/$dxref 2011.xls");
		File inputWorkbook = new File(inputFile);
		Workbook w;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);
			// Loop over first 10 column and lines
				
			for (int i = 3; i < sheet.getRows(); i++) {
					
				Cell cell = sheet.getCell(0, i);
				
				if(cell.getContents().replaceAll("'", "").trim().equalsIgnoreCase(code)) {
					
					String diagnosis = sheet.getCell(3, i).getContents().replaceAll("'", "").trim();
					return diagnosis;
				}
			}
			
			return null;
				
		} catch (BiffException e) {
			e.printStackTrace();
			return null;
		}
	}

}

