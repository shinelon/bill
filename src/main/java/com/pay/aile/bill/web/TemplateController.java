package com.pay.aile.bill.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TemplateController {
	@RequestMapping(value = "/list")
	public String gotoTemplateList(ModelMap map) {
		map.addAttribute("hello", "hello Thymeleaf!");
		return "list";
	}
}
