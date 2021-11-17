package com.search_partners.service;

import com.search_partners.model.SendMail;
import com.search_partners.repository.SendMailRepository;
import com.search_partners.service.interfaces.MailSenderService;
import com.search_partners.to.EmailMessage;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@Log4j2
@AllArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender emailSender;
    private final SendMailRepository repository;
    private final ThymeleafViewResolver thymeleafViewResolver;

    @Override
    public void sendEmail(EmailMessage emailMessage) {
        SendMail sendMail = repository.save(new SendMail(emailMessage.getSubject(), emailMessage.getTo(),
                LocalDateTime.now(), 0));
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
            sendMail.setStatus(1);
            repository.save(sendMail);

        } catch (Exception e) {
            log.error("Error send to email");
            log.error(e.getLocalizedMessage());
        }
    }
}
