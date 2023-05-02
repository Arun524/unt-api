package com.unt.untstore.service;

import java.util.Map;

public interface ThymeleafService {
	public String buildMessage(String templateName, Map<String, Object> templateModel);
}
