package com.karunakar.sms.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableAsync
@EnableScheduling
public class AppConfig extends WebMvcAutoConfiguration {

	@Autowired
	private Environment env;
	
	

	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean
	public DataSource actualDataSource() {
		return DataSourceBuilder
				.create(AppConfig.class.getClassLoader())
				.build();
	}

}
