module lk.ijse.gdse72.styleclothesleyeredarchitecture {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.jfoenix;
    requires java.sql;
    requires static lombok;


    opens lk.ijse.gdse72.styleclothesleyeredarchitecture.controller to javafx.fxml;
    opens lk.ijse.gdse72.styleclothesleyeredarchitecture to javafx.fxml;

    exports lk.ijse.gdse72.styleclothesleyeredarchitecture.controller;
    exports lk.ijse.gdse72.styleclothesleyeredarchitecture;
}