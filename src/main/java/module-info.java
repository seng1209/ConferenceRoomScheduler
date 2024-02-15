module com.example.conferenceroomscheduler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.conferenceroomscheduler to javafx.fxml;
    exports com.example.conferenceroomscheduler;
}