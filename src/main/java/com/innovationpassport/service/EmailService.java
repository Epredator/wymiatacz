package com.innovationpassport.service;

/**
 * User: trojnaradam@gmail.com
 * Date: 04.08.16
 * Time: 22:24
 */
public interface EmailService {
  public EmailMessage sendEmail(String email, String content);

  class EmailMessage {
  }
}



