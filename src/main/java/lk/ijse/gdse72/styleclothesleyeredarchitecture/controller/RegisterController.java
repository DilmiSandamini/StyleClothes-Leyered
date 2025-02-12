package lk.ijse.gdse72.styleclothesleyeredarchitecture.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.BOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.ReturnBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.UserBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.UserDTO;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private AnchorPane anchorPaneRegisterPage;

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private Label lblUserId;

    @FXML
    private JFXPasswordField txtConfirmPassword;

    @FXML
    private JFXTextField txtEmailOrPhone;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXTextField txtUsername;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);


    @FXML
    void btnSignInOnAction(ActionEvent event) throws IOException {
        anchorPaneRegisterPage.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        anchorPaneRegisterPage.getChildren().add(load);
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws IOException, SQLException {
        int userCount = userBO.getUserCount();

        if (userCount >= 2) {
            new Alert(Alert.AlertType.WARNING, "User limit reached! Cannot save more than 3 users.").show();
            return;
        }

            String userID = userBO.getNextUserId();
            lblUserId.setText(userID);
            String name = txtUsername.getText();
            String email = txtEmailOrPhone.getText();
            String password = txtPassword.getText();
            String confirmPassword = txtConfirmPassword.getText();

            String namePattern = "^[A-Za-z ]+$";
            String emailPattern = "^[\\w!#$%&'*+/=?{|}~^-]+(?:\\.[\\w!#$%&'*+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
            String passwordPattern = "^[0-9]{4,8}$";

            txtUsername.setStyle("-fx-border-color: #7367F0;");
            txtEmailOrPhone.setStyle("-fx-border-color: #7367F0;");
            txtPassword.setStyle("-fx-border-color: #7367F0;");
            txtConfirmPassword.setStyle("-fx-border-color: #7367F0;");

            boolean isValidName = name.matches(namePattern);
            boolean isValidEmail = email.matches(emailPattern);
            boolean isValidPassword1 = password.matches(passwordPattern);
            boolean isValidPassword2 = confirmPassword.matches(passwordPattern);

            boolean valid = true;

            if (!isValidName) {
                txtUsername.setStyle("-fx-border-color: red;");
                valid = false;
            }

            if (!isValidEmail) {
                txtEmailOrPhone.setStyle("-fx-border-color: red;");
                valid = false;
            }

            if (!isValidPassword1 || !isValidPassword2) {
                txtPassword.setStyle("-fx-border-color: red;");
                txtConfirmPassword.setStyle("-fx-border-color: red;");
                valid = false;
            } else if (!password.equals(confirmPassword)) {
                txtPassword.setStyle("-fx-border-color: red;");
                txtConfirmPassword.setStyle("-fx-border-color: red;");
                valid = false;
            }
            if (valid) {
                boolean isSaved = userBO.saveUser(
                        new UserDTO(
                                userID,
                                name,
                                email,
                                password
                        ));

                try {
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "User Saved Successfully").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "User Save Failed").show();
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "User Save Failed").show();
                    e.printStackTrace();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Please correct the highlighted fields").show();
            }
    }
}
