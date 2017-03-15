package com.ge.apm.ingestor.error;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
@Component
public class CustomRestErrorHandler implements ResponseErrorHandler {
	private final Logger logger = LoggerFactory
			.getLogger(getClass());
	
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		boolean hasError = false;
		if(response.getRawStatusCode() / 100 != 2) {
			hasError = true;
			String body = IOUtils.toString(response.getBody(), "UTF-8");
			String errorMessage = String.format("Rest call error : status text = %s\nstatus code = %d\nerror body %s",
					response.getStatusText(), response.getRawStatusCode(), body);
			throw new RuntimeException(errorMessage);
		}
		return hasError;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		// TODO Auto-generated method stub
		
	}



}
