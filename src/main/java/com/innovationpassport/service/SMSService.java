package com.innovationpassport.service;

/**
 * User: trojnaradam@gmail.com
 * Date: 04.08.16
 * Time: 22:23
 */
public interface SMSService {
  public SMSMessage sendSMS(String phoneNumber, String content);

  class SMSMessage {
  }
}
