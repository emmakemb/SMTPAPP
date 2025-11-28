package com.smtpapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the EmailService class.
 */
class EmailServiceTest {

    @Test
    void testIsValidEmail_ValidEmail() {
        assertTrue(EmailService.isValidEmail("test@example.com"));
        assertTrue(EmailService.isValidEmail("user.name@domain.org"));
        assertTrue(EmailService.isValidEmail("user+tag@example.co.uk"));
    }

    @Test
    void testIsValidEmail_InvalidEmail() {
        assertFalse(EmailService.isValidEmail(null));
        assertFalse(EmailService.isValidEmail(""));
        assertFalse(EmailService.isValidEmail("   "));
        assertFalse(EmailService.isValidEmail("invalid"));
        assertFalse(EmailService.isValidEmail("invalid@"));
        assertFalse(EmailService.isValidEmail("@example.com"));
    }

    @Test
    void testEmailServiceCreation() {
        EmailService service = new EmailService(
            "smtp.gmail.com", 
            587, 
            "test@example.com", 
            "mock-password", 
            true
        );
        assertNotNull(service);
    }

    @Test
    void testEmailServiceCreationWithoutTLS() {
        EmailService service = new EmailService(
            "localhost", 
            25, 
            "test@localhost", 
            "test-password", 
            false
        );
        assertNotNull(service);
    }
}
