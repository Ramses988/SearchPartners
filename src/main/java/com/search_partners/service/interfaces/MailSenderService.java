package com.search_partners.service.interfaces;

import com.search_partners.to.EmailMessage;

public interface MailSenderService {

    void sendEmail(EmailMessage mail);

}
