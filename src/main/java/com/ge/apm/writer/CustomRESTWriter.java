package com.ge.apm.writer;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ge.apm.RestProperties;
import com.ge.apm.ingestor.beans.IngestReponse;
import com.ge.apm.ingestor.beans.Ingestor;
import com.ge.apm.ingestor.beans.TagElement;
import com.ge.apm.ingestor.error.CustomRestErrorHandler;

@Component

public class CustomRESTWriter<T> implements  ItemWriter<T> { 
	private static final Logger log = LoggerFactory.getLogger(CustomRESTWriter.class);
	
	public CustomRESTWriter(RestProperties restConfig) {
		super();
		this.restConfig = restConfig;
	}


	@Autowired
	private RestProperties restConfig;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	
	private OAuth2RestTemplate restTemplate;

	@Autowired
	public Environment env;
	
	private HttpHeaders headers;
	
	@Autowired
	private MappingJackson2HttpMessageConverter messageConverter;
	
	@Autowired
	private ObjectMapper mapper;

	@PostConstruct
	public void init() {
		headers = new HttpHeaders();
		headers.set("tenant", restConfig.getTenantUuid());
		headers.set("content-type", restConfig.getContentType());
		
		//mapper = messageConverter.getObjectMapper();
	}

	
	


    @Override
    @SuppressWarnings("unchecked")
	public void write(List<? extends T> items) throws Exception {
    	CustomRestErrorHandler custerror = new CustomRestErrorHandler();
		restTemplate = restTemplate(custerror); 
		OAuth2AccessToken token = restTemplate.getAccessToken();
		String authorization = token.getTokenType() + " " + token.getValue();
		headers = new HttpHeaders();
		headers.set("tenant", restConfig.getTenantUuid());
		headers.set("content-type", restConfig.getContentType());
		
		headers.set("Authorization", authorization);
		
		messageConverter = new MappingJackson2HttpMessageConverter();
		
		mapper = messageConverter.getObjectMapper();
		
		
		
		Ingestor timeSeries = new Ingestor();
		timeSeries.getTags().addAll((List<TagElement>)items);
		
		long requestTimestamp = System.currentTimeMillis();
		logger.info("request : (" + requestTimestamp + ") : " + mapper.writeValueAsString(timeSeries));
		HttpEntity<Ingestor> entity = new HttpEntity<Ingestor>(timeSeries, CustomRESTWriter.this.headers);
		
		ResponseEntity<IngestReponse> response = restTemplate.postForEntity(restConfig.getIngestorUrl(), entity, IngestReponse.class);
		if(response.getStatusCodeValue() == org.apache.http.HttpStatus.SC_ACCEPTED || response.getStatusCodeValue() == org.apache.http.HttpStatus.SC_CREATED
				|| response.getStatusCodeValue() == org.apache.http.HttpStatus.SC_OK){
			logger.info("Created Success : (" + requestTimestamp + ") : "+ mapper.writeValueAsString(response.getBody()));
		}else{
			logger.info("Ingestion Failed : (" + requestTimestamp + ") : "+ mapper.writeValueAsString(response.getBody()) +" StatusCodeValue() -> "+ response.getStatusCodeValue() 
			+ " response.getStatusCodeValue() ->"+response.toString());
			
		}
		
		
	}
	

	public OAuth2RestTemplate restTemplate (CustomRestErrorHandler errorHandler) {
		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		logger.info("env :"+restConfig);
		logger.info(" client_username->" +restConfig.getClientUsername());
        resourceDetails.setUsername(restConfig.getClientUsername());
        resourceDetails.setPassword(restConfig.getClientPassword());
        resourceDetails.setAccessTokenUri(restConfig.getOauth2TokenUrl());
        resourceDetails.setClientId(restConfig.getClientId());
        resourceDetails.setClientSecret(restConfig.getClientSecret());
        resourceDetails.setGrantType(restConfig.getClientGrantType());
        //resourceDetails.setScope(Arrays.asList("read", "write"));

        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();

        OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);
        restTemplate.setErrorHandler(errorHandler);
        
        restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
		
        OAuth2AccessToken token = restTemplate.getAccessToken();
        restTemplate.getAccessToken();
        logger.info("#################################################################");
        logger.info(token.getTokenType() + " : " + token.getValue());
        logger.info("#################################################################");
        
        return restTemplate;
	}






	
	

}
