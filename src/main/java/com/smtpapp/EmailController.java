package com.smtpapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * Controller for the email sending form.
 * Handles user interactions and provides real-time feedback.
 */
public class EmailController {

    @FXML
    private TextField smtpHostField;
    
    @FXML
    private TextField smtpPortField;
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private CheckBox tlsCheckBox;
    
    @FXML
    private TextField recipientField;
    
    @FXML
    private TextField subjectField;
    
    @FXML
    private TextArea messageArea;
    
    @FXML
    private Button sendButton;
    
    @FXML
    private Label statusLabel;
    
    @FXML
    private ProgressIndicator progressIndicator;
    
    @FXML
    private VBox mainContainer;

    @FXML
    public void initialize() {
        // Set default values for common SMTP settings
        smtpHostField.setText("smtp.gmail.com");
        smtpPortField.setText("587");
        tlsCheckBox.setSelected(true);
        
        // Hide progress indicator initially
        progressIndicator.setVisible(false);
        
        // Add input validation listeners
        recipientField.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        subjectField.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        messageArea.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        usernameField.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        passwordField.textProperty().addListener((obs, oldVal, newVal) -> validateForm());
        
        // Initial validation
        validateForm();
    }

    @FXML
    private void handleSendEmail() {
        if (!validateInputs()) {
            return;
        }
        
        // Show sending state
        setLoadingState(true);
        statusLabel.setText("Sending email...");
        statusLabel.getStyleClass().removeAll("success", "error");
        statusLabel.getStyleClass().add("info");
        
        // Get form values
        String smtpHost = smtpHostField.getText().trim();
        int smtpPort;
        try {
            smtpPort = Integer.parseInt(smtpPortField.getText().trim());
        } catch (NumberFormatException e) {
            showError("Invalid port number");
            setLoadingState(false);
            return;
        }
        
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        boolean useTLS = tlsCheckBox.isSelected();
        String recipient = recipientField.getText().trim();
        String subject = subjectField.getText().trim();
        String message = messageArea.getText();
        
        // Create email service and send
        EmailService emailService = new EmailService(smtpHost, smtpPort, username, password, useTLS);
        
        emailService.sendEmailAsync(
            recipient,
            subject,
            message,
            () -> Platform.runLater(() -> {
                showSuccess("Email sent successfully!");
                setLoadingState(false);
                clearMessageFields();
            }),
            (error) -> Platform.runLater(() -> {
                showError("Failed to send email: " + error);
                setLoadingState(false);
            })
        );
    }

    private boolean validateInputs() {
        // Validate SMTP settings
        if (smtpHostField.getText().trim().isEmpty()) {
            showError("SMTP host is required");
            return false;
        }
        
        if (smtpPortField.getText().trim().isEmpty()) {
            showError("SMTP port is required");
            return false;
        }
        
        // Validate credentials
        if (usernameField.getText().trim().isEmpty()) {
            showError("Username/Email is required");
            return false;
        }
        
        if (!EmailService.isValidEmail(usernameField.getText().trim())) {
            showError("Invalid sender email format");
            return false;
        }
        
        if (passwordField.getText().isEmpty()) {
            showError("Password is required");
            return false;
        }
        
        // Validate recipient
        if (recipientField.getText().trim().isEmpty()) {
            showError("Recipient email is required");
            return false;
        }
        
        if (!EmailService.isValidEmail(recipientField.getText().trim())) {
            showError("Invalid recipient email format");
            return false;
        }
        
        // Validate subject
        if (subjectField.getText().trim().isEmpty()) {
            showError("Subject is required");
            return false;
        }
        
        // Validate message
        if (messageArea.getText().trim().isEmpty()) {
            showError("Message body is required");
            return false;
        }
        
        return true;
    }

    private void validateForm() {
        boolean isValid = !smtpHostField.getText().trim().isEmpty()
                && !smtpPortField.getText().trim().isEmpty()
                && !usernameField.getText().trim().isEmpty()
                && !passwordField.getText().isEmpty()
                && !recipientField.getText().trim().isEmpty()
                && !subjectField.getText().trim().isEmpty()
                && !messageArea.getText().trim().isEmpty();
        
        sendButton.setDisable(!isValid);
    }

    private void setLoadingState(boolean loading) {
        progressIndicator.setVisible(loading);
        sendButton.setDisable(loading);
        smtpHostField.setDisable(loading);
        smtpPortField.setDisable(loading);
        usernameField.setDisable(loading);
        passwordField.setDisable(loading);
        tlsCheckBox.setDisable(loading);
        recipientField.setDisable(loading);
        subjectField.setDisable(loading);
        messageArea.setDisable(loading);
    }

    private void showSuccess(String message) {
        statusLabel.setText(message);
        statusLabel.getStyleClass().removeAll("info", "error");
        statusLabel.getStyleClass().add("success");
    }

    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.getStyleClass().removeAll("info", "success");
        statusLabel.getStyleClass().add("error");
    }

    private void clearMessageFields() {
        recipientField.clear();
        subjectField.clear();
        messageArea.clear();
    }
}
