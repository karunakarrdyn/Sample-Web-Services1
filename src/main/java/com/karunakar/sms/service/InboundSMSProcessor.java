package com.karunakar.sms.service;

import com.karunakar.sms.input.SMSInput;
import com.karunakar.sms.response.Response;

public interface InboundSMSProcessor {
	Response processSMS(SMSInput input);
}
