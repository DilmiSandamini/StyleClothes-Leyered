package lk.ijse.gdse72.styleclothesleyeredarchitecture.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.BOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.UserBO;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private AnchorPane anchorPaneLogin;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        try {
            boolean isValid = userBO.isValidUser(username, password);

            if (isValid) {
                new Alert(Alert.AlertType.INFORMATION, "Login Success!").showAndWait();
                anchorPaneLogin.getChildren().clear();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBoardForm.fxml"));
                AnchorPane load = loader.load();
                anchorPaneLogin.getChildren().add(load);
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Username or Password!").showAndWait();            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws IOException {
        anchorPaneLogin.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/RegisterForm.fxml"));
        anchorPaneLogin.getChildren().add(load);
    }

}
