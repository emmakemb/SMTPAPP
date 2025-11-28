package com.smtpapp;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Service class for sending emails via SMTP with TLS/STARTTLS support.
 */
public class EmailService {

    private final String smtpHost;
    private final int smtpPort;
    private final String username;
    private final String password;
    private final boolean useTLS;

    /**
     * Creates a new EmailService with the specified SMTP configuration.
     *
     * @param smtpHost SMTP server hostname
     * @param smtpPort SMTP server port
     * @param username SMTP authentication username (email address)
     * @param password SMTP authentication password
     * @param useTLS   Whether to use TLS/STARTTLS encryption
     */
    public EmailService(String smtpHost, int smtpPort, String username, String password, boolean useTLS) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.username = username;
        this.password = password;
        this.useTLS = useTLS;
    }

    /**
     * Sends an email asynchronously.
     *
     * @param to          Recipient email address
     * @param subject     Email subject
     * @param body        Email body content
     * @param onSuccess   Callback on successful send
     * @param onError     Callback on error with error message
     */
    public void sendEmailAsync(String to, String subject, String body,
                               Runnable onSuccess, Consumer<String> onError) {
        CompletableFuture.runAsync(() -> {
            try {
                sendEmail(to, subject, body);
                if (onSuccess != null) {
                    onSuccess.run();
                }
            } catch (MessagingException e) {
                if (onError != null) {
                    onError.accept(e.getMessage());
                }
            }
        });
    }

    /**
     * Sends an email synchronously.
     *
     * @param to      Recipient email address
     * @param subject Email subject
     * @param body    Email body content
     * @throws MessagingException if sending fails
     */
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        Properties props = createMailProperties();
        
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }

    private Properties createMailProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", String.valueOf(smtpPort));
        props.put("mail.smtp.auth", "true");
        
        if (useTLS) {
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        }
        
        // Connection timeout settings
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.writetimeout", "10000");
        
        return props;
    }

    /**
     * Validates an email address format.
     *
     * @param email Email address to validate
     * @return true if the email format is valid
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }
}
