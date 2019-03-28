package com.karunakar.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.karunakar.sms.config.AppConfig;
import com.karunakar.sms.config.SecurityConfig;

@SpringBootApplication
@EnableAutoConfiguration(exclude={SessionAutoConfiguration.class})
@Import({AppConfig.class, SecurityConfig.class})
@EnableJpaRepositories(basePackages = {"com.karunakar.sms"})
@ComponentScan(basePackages = {"com.karunakar.sms"})
@EntityScan(basePackages = {"com.karunakar.sms.domain"})
@EnableTransactionManagement
@EnableAsync

public class SMSApplication {

	public static void main(String[] args) {
		SpringApplication.run(SMSApplication.class, args);
	}
	
}


