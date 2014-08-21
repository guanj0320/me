package pf.ee.mail;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.Assert;

public class DefaultMailService extends MailService implements InitializingBean {

	protected JavaMailSender mailSender;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 发送impleMailMessage.
	 */
	@Override
	public void send(SimpleMailMessage msg) {
		try {
			mailSender.send(msg);
		} catch (MailException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 使用模版发送HTML格式的邮件.
	 *
	 * @param msg		  装有to,from,subject信息的SimpleMailMessage
	 * @param templateName 模版名,模版根路径已在配置文件定义于freemakarengine中
	 * @param model		渲染模版所需的数据17
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void send(SimpleMailMessage msg, String templateName, Map model) {
		// 生成html邮件内容
		String content = generateEmailContent(templateName, model);
		MimeMessage mimeMsg = mailSender.createMimeMessage();
		try {			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, "utf-8");
			helper.setTo(msg.getTo());
			helper.setSubject(msg.getSubject());
			helper.setFrom(msg.getFrom());
			helper.setText(content, true);
		} catch (MessagingException ex) {
			log.error(ex.getMessage(), ex);
		}

		msg.setText(content);

		mailSender.send(mimeMsg);
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(mailSender, "未注入mailSender");
	}
}
