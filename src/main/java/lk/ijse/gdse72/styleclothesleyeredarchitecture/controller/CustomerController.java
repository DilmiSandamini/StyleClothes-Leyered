package lk.ijse.gdse72.styleclothesleyeredarchitecture.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.CustomerBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.Impl.ItemBOImpl;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.ItemBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl.ItemDAOImpl;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.view.tdm.CustomerTM;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Customer;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.CustomerDTO;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private AnchorPane anchorPaneCustomer;

    @FXML
    private JFXButton btnCustomerReport;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnOrderReport;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSendMail;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<CustomerTM, String> colAddress;

    @FXML
    private TableColumn<CustomerTM, String> colContactNo;

    @FXML
    private TableColumn<CustomerTM, String> colCustomerID;

    @FXML
    private TableColumn<CustomerTM, String> colEmail;

    @FXML
    private TableColumn<CustomerTM, String> colName;

    @FXML
    private TableColumn<CustomerTM, String> colRequestNameID;

    @FXML
    private JFXComboBox<String> comRequestItemID;

    @FXML
    private Label lblCustomerID;

    @FXML
    private Label lblNotify;

    @FXML
    private Label lblShowItem;

    @FXML
    private TableView<CustomerTM> tblCustomer;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtContactNo;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtRequestItemID;

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    final ItemDAOImpl itemDAO = new ItemDAOImpl();
    final ItemBO itemBO=new ItemBOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colRequestNameID.setCellValueFactory(new PropertyValueFactory<>("requestItemsID"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        comRequestItemID.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            try {
                if (newValue != null) {
                    lblShowItem.setText(itemDAO.getItemNameById(newValue));
                } else {
                    lblShowItem.setText("");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextCustomerID = customerBO.getNextCustomerId();
        lblCustomerID.setText(nextCustomerID);

        txtName.setText("");
        txtEmail.setText("");
        txtContactNo.setText("");
        txtAddress.setText("");

        loadItemIds();
        comRequestItemID.getSelectionModel().clearSelection();

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnCustomerReport.setDisable(true);
        btnSendMail.setDisable(true);
        btnOrderReport.setDisable(true);
    }

    private void loadItemIds() throws SQLException {
        ArrayList<String> itemIds = itemDAO.getAllItemIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(itemIds);
        comRequestItemID.setItems(observableList);
    }
    private void refreshTable() throws SQLException {
        ArrayList<CustomerDTO> customers = customerBO.getAllCustomers();

        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for (CustomerDTO customer : customers) {
            CustomerTM customerTM = new CustomerTM(
                    customer.getCustomerID(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getContact(),
                    customer.getAddress(),
                    customer.getRequestItemsID()
            );
            customerTMS.add(customerTM);
        }
        tblCustomer.setItems(customerTMS);
    }

    @FXML
    void btnCustomerReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String customerId = lblCustomerID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Delete This Customer?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            //Delete Customer
            boolean isDeleted = customerBO.deleteCustomer(customerId);

            if (isDeleted) {
                lblNotify.setText("Customer Delete Successfully");
                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted...!").show();
                refreshPage();
            } else {
                lblNotify.setText("Customer Delete Unsuccessfully");
                new Alert(Alert.AlertType.ERROR, "Fail To Delete Customer...!").show();
            }
        }

    }

    @FXML
    void btnOrderReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String customerID = lblCustomerID.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContactNo.getText();
        String address = txtAddress.getText();
        String requestItemsID =  comRequestItemID.getValue();

        String namePattern = "^[A-Za-z ]+$";
        String phoneNumber = "^07\\d{8}$";
        String addressPattern = "^[A-Za-z\\s\\-\\.]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidPhoneNumber = contact.matches(phoneNumber);
        boolean isValidAddress = address.matches(addressPattern);

        txtName.setStyle("-fx-border-color: #7367F0;");
        txtEmail.setStyle("-fx-border-color: #7367F0;");
        txtContactNo.setStyle("-fx-border-color: #7367F0;");
        txtAddress.setStyle("-fx-border-color: #7367F0;");

        if (!isValidName) {
            txtName.setStyle("-fx-border-color: red;");
        }

        if (!isValidPhoneNumber) {
            txtContactNo.setStyle("-fx-border-color: red;");
        }

        if (!isValidAddress) {
            txtAddress.setStyle("-fx-border-color: red;");
        }

        if (isValidName && isValidPhoneNumber && isValidAddress) {
            try {
                boolean isSaved = customerBO.saveCustomer(
                        new CustomerDTO(
                                customerID,
                                name,
                                email,
                                contact,
                                address,
                                requestItemsID
                        )
                );

                if (isSaved) {
                    lblNotify.setText("Customer Details Saved Successfully");
                    new Alert(Alert.AlertType.INFORMATION, "Customer Saved...!").show();
                    refreshPage();
                } else {
                    lblNotify.setText("Customer Details Saved Unsuccessfully");
                    new Alert(Alert.AlertType.ERROR, "Fail To Save Customer...!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                lblNotify.setText("Error saving customer: " + e.getMessage());
                new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnSendMailOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String customerID = lblCustomerID.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContactNo.getText();
        String address = txtAddress.getText();
        String requestItemsID = comRequestItemID.getValue();

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'+/=?{|}~^-]+(?:\\.[\\w!#$%&'+/=?{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phoneNumber = "^07\\d{8}$";
        String addressPattern = "^[A-Za-z\\s\\-\\.]+$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPhoneNumber = contact.matches(phoneNumber);
        boolean isValidAddress = address.matches(addressPattern);

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtContactNo.setStyle(txtContactNo.getStyle() + ";-fx-border-color: #7367F0;");
        txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: #7367F0;");
        txtRequestItemID.setStyle(txtRequestItemID.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPhoneNumber) {
            txtContactNo.setStyle(txtContactNo.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidAddress) {
            txtAddress.setStyle(txtAddress.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName  && isValidPhoneNumber && isValidAddress) {
            boolean isUpdated = customerBO.updateCustomer(
                    new CustomerDTO(
                            customerID,
                            name,
                            email,
                            contact,
                            address,
                            requestItemsID
                    ));

            if (isUpdated) {
                lblNotify.setText("Customer Details Saved Successfully");
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved...!").show();
                refreshPage();
            } else {
                lblNotify.setText("Customer Details Saved Unsuccessfully");
                new Alert(Alert.AlertType.ERROR, "Fail To Save Customer...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        CustomerTM selectedItem = tblCustomer.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblCustomerID.setText(selectedItem.getCustomerID());
            txtName.setText(selectedItem.getName());
            txtEmail.setText(selectedItem.getEmail());
            txtContactNo.setText(selectedItem.getContact());
            txtAddress.setText(selectedItem.getAddress());
            comRequestItemID.getSelectionModel().clearSelection();

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnCustomerReport.setDisable(false);
            btnSendMail.setDisable(false);
            btnOrderReport.setDisable(false);
        }
    }
}
