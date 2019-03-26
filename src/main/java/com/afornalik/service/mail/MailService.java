package com.afornalik.service.mail;

import com.afornalik.model.User;

public interface MailService {

    void sendNewPassword(User user);
}
