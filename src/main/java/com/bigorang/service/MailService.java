package com.bigorang.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * 邮件发送服务
 */
@Service
public class MailService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${spring.mail.username}")
	private String from;

	@Autowired
	private JavaMailSender mailSender;

	public void sysHello() {
		System.out.println("Hello world");

	}

	public void sendSimpleMail(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		message.setFrom(from);
		mailSender.send(message);
	}

	public void sendHtmlMail(String to, String subject, String content) {

		logger.info("发送HTML邮件开始:{},{},{}",to,subject,content);
		try {
			MimeMessage message = mailSender.createMimeMessage();

			MimeMessageHelper helper = null;
			helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);
			helper.setFrom(from);
			mailSender.send(message);
			logger.error("发送HTML邮件成功");
		} catch (MessagingException e) {
			logger.error("发送HTML邮件失败",e);
		}


	}

	public void sendAttachmentMail(String to, String subject, String content, String filePath) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content, true);
		//文件
		FileSystemResource file = new FileSystemResource(new File(filePath));

		String fileName = file.getFilename();
		helper.addAttachment(fileName, file);
		//将filePath作为数组进行遍历发送多个附件
		helper.addAttachment(fileName + "2", file);
		mailSender.send(message);

	}

	/**
	 * 发送静态图片
	 *
	 * @param to
	 * @param subject
	 * @param content
	 * @param rscPath
	 * @param rscId
	 * @throws MessagingException
	 */
	public void sendInlinResourceMail(String to, String subject, String content,
									  String rscPath, String rscId) throws MessagingException {
		//1. 创建MimeMessage实例
		MimeMessage message = mailSender.createMimeMessage();
		//2. 创建MimeMessageHelper实例
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		//发件人
		helper.setFrom(from);

		helper.setTo(to);
		helper.setSubject(subject);
		// true/false是否为html
		helper.setText(content, true);

		//文件
		FileSystemResource res = new FileSystemResource(new File(rscPath));
		//添加
		helper.addInline(rscId, res);
//		helper.addInline(rscId,res);
		mailSender.send(message);
	}
}
