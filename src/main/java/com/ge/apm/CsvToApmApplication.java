package com.ge.apm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ge.apm.ingestor.util.ProxyUtil;

@SpringBootApplication
public class CsvToApmApplication {

	public static void main(String[] args) {
		ProxyUtil.setProxy();
		SpringApplication.run(CsvToApmApplication.class, args);
	}
}
