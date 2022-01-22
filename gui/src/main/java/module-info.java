module ngBot.gui.main {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.ngbot.app to javafx.fxml;
    exports org.ngbot.app;
}