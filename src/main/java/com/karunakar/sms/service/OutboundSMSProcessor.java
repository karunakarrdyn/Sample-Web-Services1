package com.karunakar.sms.service;

import com.karunakar.sms.input.SMSInput;
import com.karunakar.sms.response.Response;

public interface OutboundSMSProcessor {
	Response processSMS(SMSInput input);

}
