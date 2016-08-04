package com.innovationpassport.service;

import org.apache.el.stream.Optional;

/**
 * User: trojnaradam@gmail.com
 * Date: 04.08.16
 * Time: 22:15
 */
public interface NotificationService {
  public void sendNotification(Optional email, Optional phoneNumber, String notificationContent);
}
