package com.pay.aile.bill.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pay.aile.bill.entity.CreditEmail;
import com.pay.aile.bill.service.mail.download.DownloadMail;

@Controller
public class EmailController {
	@Autowired
	public DownloadMail downloadMail;

	@RequestMapping(value = "/emailForm")
	public String emailForm(ModelMap map) {

		return "emailForm";
	}

	@RequestMapping(value = "/getEmailDate")
	@ResponseBody
	public String emailList(CreditEmail email) {

		return "emailList";
	}

	@RequestMapping(value = "/emailList")
	public String emailList(ModelMap map) {

		return "emailList";
	}

	@RequestMapping(value = "/getBill")
	public String getBill(CreditEmail creditEmail, String emailKey) {

		creditEmail.setEmail(creditEmail.getEmail() + "@" + emailKey);

		try {
			downloadMail.execute(creditEmail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "emailForm";
	}

	@RequestMapping(value = "/showEmail")
	public String showEmail(CreditEmail email, Model model) {
		// List<EmailFile> fileList = new
		// view.addObject(attributeName, attributeValue)
		return "showEmail";
	}
}
