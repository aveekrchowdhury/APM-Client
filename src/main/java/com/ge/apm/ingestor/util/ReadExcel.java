package com.ge.apm.ingestor.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ge.apm.ingestor.beans.DataElement;
import com.ge.apm.ingestor.beans.Ingestor;
import com.ge.apm.ingestor.beans.TagElement;

public class ReadExcel {

	public static void main(String[] args) throws IOException {
		System.out.println(Calendar.getInstance().getTimeInMillis());
		// TODO Auto-generated method stub
		// String excelFilePath = "/Users/Jain/Documents/apm/TSDataV1.xlsx";
		 //	ReadExcel reader = new ReadExcel();
		   // List<DataElement> listBooks = reader.readBooksFromExcelFile(excelFilePath);
		    //System.out.println(listBooks);
	}
	
	public static List<DataElement> readExcelData () throws IOException {
		// TODO Auto-generated method stub
		 //String excelFilePath = "/Users/Jain/Documents/apm/TSDataV1.xlsx";
		 	ReadExcel reader = new ReadExcel();
		  //  List<DataElement> listdataElement = reader.readBooksFromExcelFile(excelFilePath);
		 	List<DataElement> listdataElement = reader.generateRandomTimeSeries();
		    System.out.println(listdataElement);
		    return listdataElement;
	}
	
	private List<DataElement> generateRandomTimeSeries() {
		  List<DataElement> listDataelement = new ArrayList<>();
		 
		  DataElement dataEle = new DataElement();
		  java.util.Date date= new java.util.Date();
			
		  dataEle.setTs(Calendar.getInstance().getTimeInMillis());
		  dataEle.setV(ThreadLocalRandom.current().nextDouble(0.0, 4.1));
		  listDataelement.add(dataEle);
		  return listDataelement;
	}
	
	public List<DataElement> readBooksFromExcelFile(String excelFilePath) throws IOException {
	    List<DataElement> listDataelement = new ArrayList<>();
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	 
	    Workbook workbook = new XSSFWorkbook(inputStream);
	    Sheet firstSheet = workbook.getSheetAt(0);
	    Iterator<Row> iterator = firstSheet.iterator();
	    if(iterator.hasNext()){
	    while (iterator.hasNext()) {
	    	try {
	    	Row nextRow = iterator.next();
	        Iterator<Cell> cellIterator = nextRow.cellIterator();
	        DataElement dataEle = new DataElement();
	       
	        while (cellIterator.hasNext()) {
	            Cell nextCell = cellIterator.next();
	            int columnIndex = nextCell.getColumnIndex();
	            
	            switch (columnIndex) {
	            case 0:
	            		//System.out.println("cell "+ getCellValue(nextCell));
	            	dataEle.setTs(getTimeinMs((String)getCellValue(nextCell)));
	                break;
	            case 1:
	                dataEle.setV((Double)getCellValue(nextCell));
	                break;
	                
	            }
	 
	 
	        }
	        listDataelement.add(dataEle);
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    }
	    workbook.close();
	    inputStream.close();
	 
	    return listDataelement;
	}
	
	private Object getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return cell.getBooleanCellValue();
	 
	    case Cell.CELL_TYPE_NUMERIC:
	        return cell.getNumericCellValue();
	   
	    }
	 
	    return null;
	}
	
	
	private long getTimeinMs(String dateTime) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy hh.mm.ssa");
		
		Date date = sdf.parse(dateTime);

		//System.out.println(dateTime);
		//System.out.println("Date - Time in milliseconds : " + date.getTime());
		
		return date.getTime(); 
	}
}
