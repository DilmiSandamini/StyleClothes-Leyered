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
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.Impl.PaymentBOImpl;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.PaymentBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.CustomerDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.PaymentDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.view.tdm.PaymentTM;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private AnchorPane anchorPanePayment;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnPaymentReport;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<PaymentTM, BigDecimal> colAmount;

    @FXML
    private TableColumn<PaymentTM, String> colCustomerID;

    @FXML
    private TableColumn<PaymentTM, String> colDate;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentID;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentMethod;

    @FXML
    private JFXComboBox<String> comCustomerID;

    @FXML
    private Label lblNotify;

    @FXML
    private Label lblPaymentID;

    @FXML
    private Label lblShowCustomer;

    @FXML
    private TableView<PaymentTM> tblPayment;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXTextField txtDate;

    @FXML
    private JFXTextField txtPaymentMethod;

    PaymentBO paymentBO = (PaymentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PAYMENT);
    private final CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colPaymentID.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        try {
            refreshPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        comCustomerID.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    lblShowCustomer.setText(customerDAO.getCustomerNameById(newValue));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                lblShowCustomer.setText("");
            }
        });
    }

    private void refreshPage() throws Exception {
        refreshTable();

        String nextPaymentId = paymentBO.getNextPaymentId();
        lblPaymentID.setText(nextPaymentId);

        txtAmount.setText("");
        txtPaymentMethod.setText("");
        txtDate.setText("");

        loadCustomerIds();
        comCustomerID.getSelectionModel().clearSelection();

        btnDelete.setDisable(true);
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnPaymentReport.setDisable(true);
        btnReset.setDisable(false);
    }

    private void loadCustomerIds() throws SQLException {
        ArrayList<String> customerIds = customerDAO.getAllCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        comCustomerID.setItems(observableList);
    }

    private void refreshTable() throws SQLException {
        ArrayList<PaymentDTO> paymentDTOS = paymentBO.getAllPayments();

        ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

        for (PaymentDTO paymentDTO : paymentDTOS) {
            PaymentTM paymentTM = new PaymentTM(
                    paymentDTO.getPaymentID(),
                    paymentDTO.getAmount(),
                    paymentDTO.getPaymentMethod(),
                    paymentDTO.getDate(),
                    paymentDTO.getCustomerID()
            );
            paymentTMS.add(paymentTM);
        }
        tblPayment.setItems(paymentTMS);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws Exception {
        String paymentId = lblPaymentID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Delete This Order Payment?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = paymentBO.deletePayment(paymentId);

            if (isDeleted) {
                lblNotify.setText("Payment Delete Successfully");
                new Alert(Alert.AlertType.INFORMATION, "Payment Deleted...!").show();
                refreshPage();
            } else {
                lblNotify.setText("Payment Delete Unsuccessfully");
                new Alert(Alert.AlertType.ERROR, "Fail To Delete Payment...!").show();
            }
        }
    }

    @FXML
    void btnPaymentReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws Exception {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {
        String paymentID = lblPaymentID.getText();
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(txtAmount.getText()));
        String paymentMethod = txtPaymentMethod.getText();
        String date = txtDate.getText();
        String customerID = comCustomerID.getSelectionModel().getSelectedItem();


        txtAmount.setStyle(txtAmount.getStyle() + ";-fx-border-color: #7367F0;");
        txtPaymentMethod.setStyle(txtPaymentMethod.getStyle() + ";-fx-border-color: #7367F0;");
        txtDate.setStyle(txtDate.getStyle() + ";-fx-border-color: #7367F0;");


        PaymentDTO paymentDTO = new PaymentDTO(paymentID, amount, paymentMethod,date,customerID);
        boolean isSaved = paymentBO.savePayment(paymentDTO);
        if (isSaved) {
            lblNotify.setText("Payment Details Saved Successfully");
            new Alert(Alert.AlertType.INFORMATION, "Payment Saved...!").show();
            try{new Thread().sleep(2000);} catch(Exception e) {}
            refreshPage();
        } else {
            lblNotify.setText("Payment Details Saved Unsuccessfully");
            new Alert(Alert.AlertType.ERROR, "Fail To Save Payment...!").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws Exception {
        String paymentID = lblPaymentID.getText();
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(txtAmount.getText()));
        String paymentMethod = txtPaymentMethod.getText();
        String date = txtDate.getText();
        String customerID = comCustomerID.getSelectionModel().getSelectedItem();


        txtAmount.setStyle(txtAmount.getStyle() + ";-fx-border-color: #7367F0;");
        txtPaymentMethod.setStyle(txtPaymentMethod.getStyle() + ";-fx-border-color: #7367F0;");
        txtDate.setStyle(txtDate.getStyle() + ";-fx-border-color: #7367F0;");


        PaymentDTO paymentDTO = new PaymentDTO(paymentID, amount, paymentMethod,date,customerID);
        boolean isSaved = paymentBO.updatePayment(paymentDTO);
        if (isSaved) {
            lblNotify.setText("Payment Details Update Successfully");
            new Alert(Alert.AlertType.INFORMATION, "Payment Updated...!").show();
            try{new Thread().sleep(2000);} catch(Exception e) {}
            refreshPage();
        } else {
            lblNotify.setText("Payment Details Update Unsuccessfully");
            new Alert(Alert.AlertType.ERROR, "Fail To Update Payment...!").show();
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        PaymentTM selectedPayment = tblPayment.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            lblPaymentID.setText(selectedPayment.getPaymentID());
            comCustomerID.setValue(selectedPayment.getCustomerID());
            txtDate.setText(selectedPayment.getDate());
            txtAmount.setText(selectedPayment.getAmount().toString());
            txtPaymentMethod.setText(selectedPayment.getPaymentMethod());

            btnReset.setDisable(false);
            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnPaymentReport.setDisable(false);
        }

    }

}

