package com.karunakar.sms.test.unit;

import static org.junit.Assert.assertNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.karunakar.sms.SMSApplication;
import com.karunakar.sms.input.SMSInput;
import com.karunakar.sms.input.validation.InputValidator;
import com.karunakar.sms.response.Response;
import com.karunakar.sms.response.exception.ValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SMSApplication.class)
//@TestPropertySource(locations={"classpath:application.properties"})
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ValidationTest {
	
	@Autowired
	InputValidator inputValidator;
	
	String from="4924195509198";
	String to="4924195509196";
	
	@Test
	public void validInput() {
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setTo(to);
		input.setText("hello,...");
		Response res=inputValidator.doInputValidation(input);
		assertNull(res);
    }
	
	@Test(expected = ValidationException.class)
	public void invalidFromInput() {
		SMSInput input=new SMSInput();
		input.setFrom("89");
		input.setTo(to);
		input.setText("hello,....");
		inputValidator.doInputValidation(input);
    }
	
	@Test(expected = ValidationException.class)
	public void invalidToInput() {
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setTo("49");
		input.setText("hello,....");
		inputValidator.doInputValidation(input);
    }
	
	@Test(expected = ValidationException.class)
	public void invalidTextInput() {
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setTo(to);
		input.setText("");
		inputValidator.doInputValidation(input);
    }
	
	@Test(expected = ValidationException.class)
	public void missingFromInInput() {
		SMSInput input=new SMSInput();
		input.setTo(to);
		input.setText("hello,....");
		inputValidator.doInputValidation(input);
    }
	
	@Test(expected = ValidationException.class)
	public void missingToInInput() {
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setText("hello,....");
		inputValidator.doInputValidation(input);
    }
	
	@Test(expected = ValidationException.class)
	public void missingTextInInput() {
		SMSInput input=new SMSInput();
		input.setFrom(from);
		input.setTo(to);
		inputValidator.doInputValidation(input);
    }

}
