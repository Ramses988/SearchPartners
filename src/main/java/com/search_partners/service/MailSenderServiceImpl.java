package com.search_partners.service;

import com.search_partners.service.interfaces.MailSenderService;
import com.search_partners.to.EmailMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@Log4j2
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender emailSender;
    private final ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    public MailSenderServiceImpl(JavaMailSender emailSender, ThymeleafViewResolver thymeleafViewResolver) {
        this.emailSender = emailSender;
        this.thymeleafViewResolver = thymeleafViewResolver;
    }

    @Override
    public void sendEmail(EmailMessage emailMessage) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Context context = new Context();
            context.setVariables(emailMessage.getContext());
            String text = thymeleafViewResolver.getTemplateEngine().process("mails/" + emailMessage.getTemplateLocation(), context);

            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());
            mimeMessageHelper.setFrom("info@find-team.one", "FindTeam");
            mimeMessageHelper.setText(text, true);
            emailSender.send(message);

        } catch (Exception e) {
            log.error("Error send to email");
            log.error(e.getLocalizedMessage());
        }
    }
}
