package com.team_2.paceplanner.services.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.team_2.paceplanner.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Override
    public void sendBatteryLowAlert(Long userId) {
        sendPushNotification(userId, "Battery Low", "Your device battery is below 20%. Please charge it.");
    }

    @Override
    public void sendDisconnectedAlert(Long userId) {
        sendPushNotification(userId, "Device Disconnected", "Your device has been disconnected or inactive.");
    }

    private void sendPushNotification(Long userId, String title, String body) {
        try {
            Message message = Message.builder()
                    .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                    .setTopic("user-" + userId)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            logger.info("Successfully sent message: {}", response);
        } catch (FirebaseMessagingException e) {
            logger.error("Error sending push notification: {}", e.getMessage());
        }
    }
}