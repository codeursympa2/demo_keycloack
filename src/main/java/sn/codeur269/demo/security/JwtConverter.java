package sn.codeur269.demo.security;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;


@Component
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken>{

	private final JwtGrantedAuthoritiesConverter authoritiesConverter;
	private final JwtConverterProperties converterProperties;
	
	
	public JwtConverter(
			JwtConverterProperties converterProperties) {
		super();
		this.authoritiesConverter = new JwtGrantedAuthoritiesConverter();
		this.converterProperties = converterProperties;
	}


	@Override
	public AbstractAuthenticationToken convert(Jwt jwt) {
		Collection<GrantedAuthority> authorities=Stream.concat(
				authoritiesConverter.convert(jwt).stream(),
				extractRessourceRoles(jwt).stream())
				.collect(Collectors.toSet());
		
	
		return new JwtAuthenticationToken(jwt,authorities,getPrincipalClaimName(jwt));
	}

	
	private Collection<GrantedAuthority> extractRessourceRoles(Jwt jwt) {
		Map<String, Object> ressourceAccess= jwt.getClaim("resource_access");
		Map<String, Object> ressource;
		Collection<String> ressourceRoles;
		
		//Pas de roles on retourne rien
		if(ressourceAccess == null
				|| (ressource =(Map<String, Object>) ressourceAccess.get(converterProperties.getRessourceId())) == null
				|| (ressourceRoles = (Collection<String>) ressource.get("roles")) == null
				) {
			return Set.of();
		}
		
		return ressourceRoles.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
				.collect(Collectors.toSet());
	}


	private String getPrincipalClaimName(Jwt jwt) {
		String claimName= JwtClaimNames.SUB;
		
		if(converterProperties.getPrincipalAttribute() != null) {
			claimName= converterProperties.getPrincipalAttribute();
		}	
		
		return jwt.getClaim(claimName);
	}
	
}
