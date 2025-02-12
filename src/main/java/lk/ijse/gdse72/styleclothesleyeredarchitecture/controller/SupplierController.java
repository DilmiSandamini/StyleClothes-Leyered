package lk.ijse.gdse72.styleclothesleyeredarchitecture.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.BOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.SupplierBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.SupplierDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.view.tdm.SupplierTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    @FXML
    private AnchorPane anchorPaneSupplier;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSendMailSupplier;

    @FXML
    private JFXButton btnSupplierReport;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<SupplierTM, String> colContactNo;

    @FXML
    private TableColumn<SupplierTM, String> colEmail;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierID;

    @FXML
    private TableColumn<SupplierTM, String> colSupplierName;

    @FXML
    private Label lblNotify;

    @FXML
    private Label lblSupplierID;

    @FXML
    private TableView<SupplierTM> tblSupplier;

    @FXML
    private JFXTextField txtContactNO;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtSupplierName;

    SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contact"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextSupplierID = supplierBO.getNextSupplierId();
        lblSupplierID.setText(nextSupplierID);

        txtSupplierName.setText("");
        txtEmail.setText("");
        txtContactNO.setText("");

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnSupplierReport.setDisable(true);
        btnReset.setDisable(false);
        btnSendMailSupplier.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<SupplierDTO> supplierDTOS = supplierBO.getAllSuppliers();
        ObservableList<SupplierTM> supplierTMS = FXCollections.observableArrayList();

        for (SupplierDTO supplierDTO : supplierDTOS) {
            SupplierTM supplierTM = new SupplierTM(
                    supplierDTO.getSupplierID(),
                    supplierDTO.getName(),
                    supplierDTO.getEmail(),
                    supplierDTO.getContact()
            );
            supplierTMS.add(supplierTM);
        }
        tblSupplier.setItems(supplierTMS);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String supplierId = lblSupplierID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Delete This Supplier?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = supplierBO.deleteSupplier(supplierId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Delete...!").show();
                lblNotify.setText("Supplier Successfully Delete!");
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail To Delete Supplier...!").show();
                lblNotify.setText("Failed To Delete Supplier.");
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String supplierId = lblSupplierID.getText();
        String name = txtSupplierName.getText();
        String email = txtEmail.getText();
        String contact = txtContactNO.getText();

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'+/=?{|}~^-]+(?:\\.[\\w!#$%&'+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phoneNumberPattern = "^07\\d{8}$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhoneNumber = contact.matches(phoneNumberPattern);

        txtSupplierName.setStyle(txtSupplierName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtContactNO.setStyle(txtContactNO.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidName) {
            txtSupplierName.setStyle(txtSupplierName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPhoneNumber) {
            txtContactNO.setStyle(txtContactNO.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidPhoneNumber) {
            SupplierDTO supplierDTO = new SupplierDTO(supplierId, name, email, contact);

            boolean isSaved = supplierBO.saveSupplier(supplierDTO);
            if (isSaved) {
                lblNotify.setText("Supplier Details Saved Successfully");
                new Alert(Alert.AlertType.INFORMATION, "Supplier Saved...!").show();
                refreshPage();
            } else {
                lblNotify.setText("Supplier Details Saved Unsuccessfully");
                new Alert(Alert.AlertType.ERROR, "Fail To Save Supplier...!").show();
            }
        }
    }

    @FXML
    void btnSendMailSupplierOnAction(ActionEvent event) throws SQLException {
        SupplierTM selectedItem = tblSupplier.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select customer..!");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MailSendForm.fxml"));
            Parent load = loader.load();

            MailSendController sendMailController = loader.getController();

            String email = selectedItem.getEmail();
            sendMailController.setCustomerEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send email");

            stage.initModality(Modality.APPLICATION_MODAL);

            Window underWindow = btnUpdate.getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load ui..!");
            e.printStackTrace();
        }

    }

    @FXML
    void btnSupplierReportOnAction(ActionEvent event) throws SQLException {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String supplierId = lblSupplierID.getText();
        String name = txtSupplierName.getText();
        String email = txtEmail.getText();
        String contact = txtContactNO.getText();

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'+/=?{|}~^-]+(?:\\.[\\w!#$%&'+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phoneNumber = "^07\\d{8}$";


        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhoneNumber = contact.matches(phoneNumber);

        txtSupplierName.setStyle(txtSupplierName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtContactNO.setStyle(txtContactNO.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidName) {
            txtSupplierName.setStyle(txtSupplierName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPhoneNumber) {
            txtContactNO.setStyle(txtContactNO.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidPhoneNumber) {
            SupplierDTO supplierDTO = new SupplierDTO(supplierId, name, email, contact);

            boolean isUpdated = supplierBO.updateSupplier(supplierDTO);
            if (isUpdated) {
                lblNotify.setText("Supplier Details Update Successfully");
                new Alert(Alert.AlertType.INFORMATION, "Supplier Updated...!").show();
                try {
                    new Thread().sleep(2000);
                } catch (Exception e) {
                }
                refreshPage();
            } else {
                lblNotify.setText("Supplier Details Updated Unsuccessfully");
                new Alert(Alert.AlertType.ERROR, "Fail To Update Supplier...!").show();
            }
        }
    }
    public void onClickTable(javafx.scene.input.MouseEvent mouseEvent) {
        SupplierTM selectedSupplier = tblSupplier.getSelectionModel().getSelectedItem();

        if (selectedSupplier != null) {
            lblSupplierID.setText(selectedSupplier.getSupplierID());
            txtSupplierName.setText(selectedSupplier.getName());
            txtContactNO.setText(selectedSupplier.getContact());
            txtEmail.setText(selectedSupplier.getEmail());

            btnReset.setDisable(false);
            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnSendMailSupplier.setDisable(false);
            btnSupplierReport.setDisable(false);
        }
    }
}

