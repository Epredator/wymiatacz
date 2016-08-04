package com.innovationpassport.service;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * User: trojnaradam@gmail.com
 * Date: 04.08.16
 * Time: 22:59
 */
@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceTest {

  @Mock
  EmailService emailService;

  public NotificationServiceTest(){
    MockitoAnnotations.initMocks(this);
  }
}
