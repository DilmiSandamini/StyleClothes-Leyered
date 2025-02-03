package lk.ijse.gdse72.styleclothesshop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WelcomeController {

    @FXML
    private AnchorPane anchorpaneWelcome;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnRegister;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        anchorpaneWelcome.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        anchorpaneWelcome.getChildren().add(load);
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws IOException {
        anchorpaneWelcome.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/RegisterForm.fxml"));
        anchorpaneWelcome.getChildren().add(load);
    }

}
