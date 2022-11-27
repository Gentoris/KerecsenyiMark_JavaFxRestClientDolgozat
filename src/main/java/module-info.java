module hu.petrik.kerecsenyimark_javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens hu.petrik.kerecsenyimark_javafxrestclientdolgozat to javafx.fxml, com.google.gson ;
    exports hu.petrik.kerecsenyimark_javafxrestclientdolgozat;
}