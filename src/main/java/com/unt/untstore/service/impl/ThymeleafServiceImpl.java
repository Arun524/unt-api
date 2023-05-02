package com.unt.untstore.service.impl;

import com.unt.untstore.service.ThymeleafService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.util.Map;

@Service
public class ThymeleafServiceImpl implements ThymeleafService {

	@Autowired
	private SpringTemplateEngine thymeleafTemplateEngine;

	@Override
	public String buildMessage(String templateName, Map<String, Object> templateModel) {
		Context thymeleafContext = new Context();
		thymeleafContext.setVariables(templateModel);
		System.out.println("test>>>>"+thymeleafContext.toString());
		String htmlText = thymeleafTemplateEngine.process(templateName, thymeleafContext);
		return htmlText;
	}

}
