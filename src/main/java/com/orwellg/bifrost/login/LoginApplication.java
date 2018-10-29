package com.orwellg.bifrost.login;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.orwellg.umbrella.spring.context.SpringProfileSettingApplicationContextInitializer;

@SpringBootApplication
public class LoginApplication {
	
    public final static Logger LOG = LogManager.getLogger(LoginApplication.class);


	public static void main(String[] args) {
		
		Map<String, Object> defaultPropertyValues = new HashMap<String, Object>();
		defaultPropertyValues.put("swagger", "true");
		defaultPropertyValues.put("server.ssl.key-store-type", ".");
		defaultPropertyValues.put("server.ssl.key-store", "-");
		defaultPropertyValues.put("server.ssl.key-store-password", "XXXXXXX");
		defaultPropertyValues.put("server.ssl.key-alias", "cert");
		defaultPropertyValues.put("truststore.path", "-");
		defaultPropertyValues.put("truststore.pass", "XXXXXXX");
		defaultPropertyValues.put("oauthprop.clientId", "bifrost");
		defaultPropertyValues.put("oauthprop.clientSecret", "XXXXXXX");
		defaultPropertyValues.put("oauthprop.checkTokenUrl", "-");
		defaultPropertyValues.put("oauthprop.userInfoUrl", "-");
		defaultPropertyValues.put("authServer", "-");
		
		List<String> zookeeperPaths = Arrays.asList("/com/orwellg/bifrost", "/com/orwellg/yggdrasil/id-generator", "/com/orwellg/yggdrasil/kafka", "/com/orwellg/yggdrasil/network");
		
		try {
			new SpringApplicationBuilder(LoginApplication.class)
					.initializers(new SpringProfileSettingApplicationContextInitializer("./config/application.properties", zookeeperPaths, defaultPropertyValues))
					.run(args);
		}catch(Exception e){
				LOG.error("[ContractBifrostApplication] Error running Contract {}", e.getMessage(), e);
		}
	}

}
