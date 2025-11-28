module com.smtpapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.mail;
    requires jakarta.activation;

    opens com.smtpapp to javafx.fxml;
    exports com.smtpapp;
}
