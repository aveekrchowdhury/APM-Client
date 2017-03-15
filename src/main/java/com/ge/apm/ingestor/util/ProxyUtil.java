package com.ge.apm.ingestor.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyUtil {
	//should be set as host:port or http://host:port
	public static String httpProxyProperty = "http_proxy";
	
	private static final Logger log = LoggerFactory
			.getLogger(ProxyUtil.class);
	
	public static String getHost() {
		String host = System.getenv(httpProxyProperty);
		host = "http://proxy-src.research.ge.com:8080";
		if(host != null) {
			host = host.replace("http://", "").replace("https://", "");
			host = host.split(":")[0];
		} else {
			log.warn(httpProxyProperty + " system property found null, returning null host");
		}
		return host;
	}
	
	public static String getPort() {
		String port = System.getenv(httpProxyProperty);
		port = "http://proxy-src.research.ge.com:8080";
		if(port != null) {
			port = port.replace("http://", "").replace("https://", "");
			port = port.split(":")[1];
			port=port.replace("/", "");
		} else {
			log.warn(httpProxyProperty + " system property found null, returning 80 port");
			port = "80";
		}
		return port;
	}
	
	public static void setProxy() {
		String host = getHost();
		String port = getPort();
		
		if(host != null && port != null) {
			String proxyMessage = "";
			proxyMessage += "\n********************************************************\n";
			proxyMessage += "***" + "Setting proxy host : " + host + " and port : " + port + " *******\n";
			proxyMessage += "\n********************************************************\n";
			log.info(proxyMessage);
			
			System.setProperty("http.proxyHost", host);
	        System.setProperty("http.proxyPort", port);
	        System.setProperty("https.proxyHost", host);
	        System.setProperty("https.proxyPort", port);
		}
	}
	
	
}
