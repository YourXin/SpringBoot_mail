# SpringBoot_mail
# SpringBoot发送邮件的示例-学习来自慕课网
---
## 主要实现以下几种发送形式
1. 发送简单邮件
2. 发送HTML邮件(个人感觉和简单邮件是差不多的,纯属个人观点)
3. 发送带附件的邮件
4. 发送静态图片(一张和多张)


---

1. pom.xml中引入spring-boot-starter-mail
```
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-mail</artifactId>
	</dependency>
```
2. application.properties文件中设置你的邮箱参数 
关键参数4个,YourEmail、yourPass修改为自己的，yourPass可能不是直接网页版的登录密码，而是需要授权码 
具体的请查看各大邮箱服务商的配置参数
```
spring.mail.host=smtp.qq.com
spring.mail.username=YourEmail
spring.mail.password=yourPass
spring.mali.default-encoding=UTF-8
```

3. 在Service中使用自动装配
```
  @Autowired
	private JavaMailSender mailSender;
```
4. 再调用各发送类型方法即可
具体请查看src\main\java\com\bigorang\service的内容

---


