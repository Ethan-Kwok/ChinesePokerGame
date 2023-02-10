module com.example.chinesepokergame2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chinesepokergame2 to javafx.fxml;
    exports com.example.chinesepokergame2;
}