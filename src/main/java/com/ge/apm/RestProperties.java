package com.ge.apm;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rest")

public class RestProperties {

	private String clientUsername;
	private String clientPassword;
	private String clientGrantType;
	private String clientId;
	private String oauth2TokenUrl;
	private String ingestorUrl;
	private String tenantUuid;
	private String contentType;
	private String clientSecret;
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public String getClientUsername() {
		return clientUsername;
	}
	public void setClientUsername(String clientUsername) {
		this.clientUsername = clientUsername;
	}
	public String getClientPassword() {
		return clientPassword;
	}
	public void setClientPassword(String clientPassword) {
		this.clientPassword = clientPassword;
	}
	public String getClientGrantType() {
		return clientGrantType;
	}
	public void setClientGrantType(String clientGrantType) {
		this.clientGrantType = clientGrantType;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getOauth2TokenUrl() {
		return oauth2TokenUrl;
	}
	public void setOauth2TokenUrl(String oauth2TokenUrl) {
		this.oauth2TokenUrl = oauth2TokenUrl;
	}
	public String getIngestorUrl() {
		return ingestorUrl;
	}
	public void setIngestorUrl(String ingestorUrl) {
		this.ingestorUrl = ingestorUrl;
	}
	public String getTenantUuid() {
		return tenantUuid;
	}
	public void setTenantUuid(String tenantUuid) {
		this.tenantUuid = tenantUuid;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
