package lk.ijse.gdse72.styleclothesleyeredarchitecture.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.BOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.EmployeeBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.EmployeeDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.view.tdm.EmployeeTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    private AnchorPane anchorPaneEmployee;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEmployeeReport;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<EmployeeTM, String> colContactNo;

    @FXML
    private TableColumn<EmployeeTM, String> colEmployeeID;

    @FXML
    private TableColumn<EmployeeTM, String> colName;

    @FXML
    private TableColumn<EmployeeTM, String> colPosition;

    @FXML
    private Label lblEmployeeID;

    @FXML
    private Label lblNotify;

    @FXML
    private TableView<EmployeeTM> tblEmployee;

    @FXML
    private JFXTextField txtContactNo;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPosition;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextEmployeeID = employeeBO.getNextEmployeeId();
        lblEmployeeID.setText(nextEmployeeID);

        txtName.setText("");
        txtContactNo.setText("");
        txtPosition.setText("");

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnEmployeeReport.setDisable(true);
        btnReset.setDisable(false);
    }

    private void refreshTable() throws SQLException {
        ArrayList<EmployeeDTO> employeeDTOS = employeeBO.getAllEmployees();
        ObservableList<EmployeeTM> employeeTMS = FXCollections.observableArrayList();

        for (EmployeeDTO employeeDTO : employeeDTOS) {
            EmployeeTM employeeTM = new EmployeeTM(
                    employeeDTO.getEmployeeID(),
                    employeeDTO.getName(),
                    employeeDTO.getContact(),
                    employeeDTO.getPosition()
            );
            employeeTMS.add(employeeTM);
        }
        tblEmployee.setItems(employeeTMS);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String employeeId = lblEmployeeID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Delete This Employee?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = employeeBO.deleteEmployee(employeeId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Employee Delete...!").show();
                lblNotify.setText("Employee Successfully Delete!");
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail To Delete Employee...!").show();
                lblNotify.setText("Failed To Delete Employee.");
            }
        }
    }

    @FXML
    void btnEmployeeReportOnAction(ActionEvent event) throws SQLException {
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String employeeID = lblEmployeeID.getText();
        String name = txtName.getText();
        String contact = txtContactNo.getText();
        String position = txtPosition.getText();

        String namePattern = "^[A-Za-z ]+$";
        String phonePattern = "^[0-9]{10}$"; // 10-digit phone number validation

        boolean isValidName = name.matches(namePattern);
        boolean isValidContact = contact.matches(phonePattern);

        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Invalid Name! Please enter a valid name.").show();
            return;
        }
        if (!isValidContact) {
            new Alert(Alert.AlertType.ERROR, "Invalid Contact! Please enter a 10-digit phone number.").show();
            return;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO(employeeID, name, contact, position);

        boolean isSaved = employeeBO.saveEmployee(employeeDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Employee Saved Successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to Save Employee!").show();
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String employeeID = lblEmployeeID.getText();
        String name = txtName.getText();
        String contact = txtContactNo.getText();
        String position = txtPosition.getText();

        String namePattern = "^[A-Za-z ]+$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidContact = contact.matches(phonePattern);

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtContactNo.setStyle(txtContactNo.getStyle() + ";-fx-border-color: #7367F0;");
        txtPosition.setStyle(txtPosition.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidContact) {
            txtContactNo.setStyle(txtContactNo.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidContact) {
            EmployeeDTO employeeDTO = new EmployeeDTO(employeeID, name, contact, position);

            boolean isUpdated = employeeBO.updateEmployee(employeeDTO);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Employee Updated...!").show();
                lblNotify.setText("Employee Successfully Updated!");
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail To Update Employee...!").show();
                lblNotify.setText("Failed To Update Employee.");
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        EmployeeTM selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblEmployeeID.setText(selectedItem.getEmployeeID());
            txtName.setText(selectedItem.getName());
            txtContactNo.setText(selectedItem.getContact());
            txtPosition.setText(selectedItem.getPosition());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
}