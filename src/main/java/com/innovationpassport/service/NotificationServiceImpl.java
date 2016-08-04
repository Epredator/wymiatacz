package com.innovationpassport.service;

/**
 * User: trojnaradam@gmail.com
 * Date: 04.08.16
 * Time: 22:20
 */
public class NotificationServiceImpl {
  public NotificationServiceImpl(EmailService emailService, SMSService smsService) {
    this.emailService = emailService;
    this.smsService = smsService;
  }

  private SMSService smsService;

  private EmailService emailService;


}
