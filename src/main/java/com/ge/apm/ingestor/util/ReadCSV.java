package com.ge.apm.ingestor.util;


import java.io.FileReader;
import java.io.IOException;

import com.ge.apm.ingestor.beans.Ingestor;
import com.ge.apm.transformer.TimeSeriesTransformer;

import au.com.bytecode.opencsv.CSVReader;

public class ReadCSV {

	public static CSVReader readCSV() throws IOException{
	   CSVReader reader = new CSVReader(new FileReader("C:\\Users\\502620569\\Documents\\Work\\Digital Thread\\Projects\\GRID_IQ\\APM_DataIngest\\src\\main\\resources\\newassetData.csv"));
	 return reader;
     
	}
	
	public static void main(String[] args) throws IOException{
		Ingestor timeSeries = new Ingestor();
		TimeSeriesTransformer transformer = new TimeSeriesTransformer();
		//transformer.transformCSV(readCSV());
		System.out.println("tagSize :"+timeSeries.getTags().size());
	}
}
