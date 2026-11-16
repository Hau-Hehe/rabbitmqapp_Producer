package com.trunghau.rabbitmqapp.controller;

import com.trunghau.rabbitmqapp.service.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private static final Logger Log = LoggerFactory.getLogger(MessageController.class);

    private final MessageProducer messageProducer;

    @PostMapping("/email-notification")
    public ResponseEntity<Map<String, String>> sendEmailNotification(
            @RequestParam String recipient,
            @RequestParam String subject,
            @RequestParam String content,
            @RequestParam (defaultValue = "MEDIUM") String priority
    ) {
        Log.info("Request to send Email notification to: {}", recipient);
        try{
            String messageId =  messageProducer.sendEmailNotification(recipient, subject, content, priority);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "messageId", messageId,
                    "message", "Email-Notification sent to RabbitMQ successfully"
            ));
        }catch(Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                   "status", "error",
                   "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/sms-notification")
    public ResponseEntity<Map<String, String>> sendSmsNotification(
            @RequestParam String recipient,
            @RequestParam String content
    ) {
        Log.info("Request to send Sms notification to: {}", recipient);
        try{
            String messageId =  messageProducer.sendSmsNotification(recipient, content);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "messageId", messageId,
                    "message", "SMS-Notification sent to RabbitMQ successfully"
            ));
        }catch(Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }
}
