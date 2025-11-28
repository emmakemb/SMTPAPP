# SMTP Mail Application

A lightweight JavaFX desktop application for sending emails via SMTP. Features a clean, responsive interface with real-time feedback.

## Features

- **Clean, Responsive Interface**: Modern JavaFX UI with intuitive form layout
- **Real-time Feedback**: Progress indicators and status messages during email sending
- **Secure Authentication**: Supports TLS/STARTTLS encryption for secure email transmission
- **Cross-Platform**: Compatible with Windows, macOS, and Linux
- **Easy Configuration**: Pre-configured for common SMTP servers (Gmail, etc.)
- **Input Validation**: Email address format validation and form validation
- **Automatic Window Resizing**: Responsive layout that adapts to window size changes

## Requirements

- Java 11 or higher
- Maven 3.6 or higher

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/emmakemb/SMTPAPP.git
   cd SMTPAPP
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn javafx:run
   ```

## Usage

1. **Configure SMTP Settings**:
   - Enter your SMTP server host (e.g., `smtp.gmail.com`)
   - Enter the port number (typically `587` for TLS)
   - Enter your email address as the username
   - Enter your password or app-specific password
   - Enable TLS/STARTTLS for secure connections (recommended)

2. **Compose Your Email**:
   - Enter the recipient's email address
   - Enter the email subject
   - Type your message in the message body area

3. **Send**:
   - Click the "Send Email" button
   - Watch the progress indicator for sending status
   - Receive confirmation or error feedback

## Gmail Configuration

For Gmail, you need to:
1. Use `smtp.gmail.com` as the SMTP host
2. Use port `587`
3. Enable TLS/STARTTLS
4. Use an [App Password](https://support.google.com/accounts/answer/185833) instead of your regular password

## Technologies Used

- **JavaFX 17**: Modern UI framework for Java applications
- **Jakarta Mail API**: For SMTP email functionality
- **Maven**: Build and dependency management
- **JUnit 5**: Testing framework

## Project Structure

```
src/
├── main/
│   ├── java/com/smtpapp/
│   │   ├── SMTPApp.java         # Main application entry point
│   │   ├── EmailController.java # UI controller with form handling
│   │   └── EmailService.java    # SMTP email service
│   └── resources/com/smtpapp/
│       ├── email-view.fxml      # FXML layout
│       └── styles.css           # CSS styling
└── test/
    └── java/com/smtpapp/
        └── EmailServiceTest.java # Unit tests
```

## License

This project is open source and available under the MIT License.
