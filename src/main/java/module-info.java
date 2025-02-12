module lk.ijse.gdse72.styleclothesleyeredarchitecture {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.jfoenix;
    requires java.sql;
    requires static lombok;
    requires java.mail;


    opens lk.ijse.gdse72.styleclothesleyeredarchitecture.controller to javafx.fxml;
    opens lk.ijse.gdse72.styleclothesleyeredarchitecture to javafx.fxml;
    opens lk.ijse.gdse72.styleclothesleyeredarchitecture.view.tdm to javafx.base;

    exports lk.ijse.gdse72.styleclothesleyeredarchitecture.controller;
    exports lk.ijse.gdse72.styleclothesleyeredarchitecture;
}