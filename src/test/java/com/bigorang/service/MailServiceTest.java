package com.bigorang.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

	@Resource
	private MailService mailService;


	@Resource
	TemplateEngine templateEngine;


	@Test
	public void testSendSimpleMail() {
		mailService.sendSimpleMail("bigzx@qq.com", "Hello", "第一封SpringBoot邮件");
	}

	@Test
	public void testSendHTMLMail() {
		String content = "<html>\n" +
				"<body>\n" + "<h3>HTML邮件</h3>\n" + "</body>" +
				"</html>";
		mailService.sendHtmlMail("bigzx@qq.com", "HTML邮件", content);

	}

	@Test
	public void testSendAttachmentMail() {
		String filePath = "d:\\51YMClient.zip";
		try {
			mailService.sendAttachmentMail("bigzx@qq.com", "HTML邮件", "附件", filePath);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSendInlineMail() {
		//String imgPath = "d:\\QQshot20180628213321.png";
		String imgPath = "c:/users/bigorang/desktop/68BCDABC58AB2216606F694A438DACC2.jpg";
		String rscId = "bg01";//不能带有中文字符串
		String content = "<html>\n" +
				"<body>\n" +
				"<img src=\'cid:" + rscId + "\'>" + "</img>" +
				"<img src=\'cid:" + rscId + "\'>" + "</img>" +
				"</body></html>";
		try {
			mailService.sendInlinResourceMail("bigzx@qq.com", "图片邮件",
					content, imgPath, rscId);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSendTemplateMail() throws MessagingException {
		Context context = new Context();
		context.setVariable("id", "006");
		String emailContent = templateEngine.process("emailTemplate", context);
		mailService.sendHtmlMail("bigzx@qq.com", "模板邮件", emailContent);
	}

}
