package sn.codeur269.demo.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
public class JwtConverterProperties {
	private String ressourceId;
	private String principalAttribute;
	
	public String getRessourceId() {
		return ressourceId;
	}
	public void setRessourceId(String ressourceId) {
		this.ressourceId = ressourceId;
	}
	public String getPrincipalAttribute() {
		return principalAttribute;
	}
	public void setPrincipalAttribute(String principalAttribute) {
		this.principalAttribute = principalAttribute;
	}
	
	
}
