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
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.ReturnBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.ReturnDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Return;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.view.tdm.ReturnTM;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReturnController implements Initializable {

    @FXML
    private AnchorPane anchorPaneReturn;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnReturnReport;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<ReturnTM, String> colDate;

    @FXML
    private TableColumn<ReturnTM, String> colOrderID;

    @FXML
    private TableColumn<ReturnTM, String> colItemName;

    @FXML
    private TableColumn<ReturnTM, BigDecimal> colItemPrice;

    @FXML
    private TableColumn<ReturnTM, String> colReason;

    @FXML
    private TableColumn<ReturnTM, String> colReturnID;

    @FXML
    private JFXComboBox<String> comOrderID;

    @FXML
    private Label lblNotify;

    @FXML
    private Label lblReturnID;

    @FXML
    private TableView<ReturnTM> tblReturn;

    @FXML
    private JFXTextField txtDate;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private JFXTextField txtItemPrice;

    @FXML
    private JFXTextField txtReason;

    ReturnBO returnBO = (ReturnBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RETURN);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colReturnID.setCellValueFactory(new PropertyValueFactory<>("returnID"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        colReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));

        try {
            refreshPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws Exception {
        refreshTable();
        String nextReturnId = returnBO.getNextReturnId();
        lblReturnID.setText(nextReturnId);

        txtItemName.setText("");
        txtItemPrice.setText("");
        txtReason.setText("");
        txtDate.setText("");

        loadOrderIds();
        comOrderID.getSelectionModel().clearSelection();

        btnReset.setDisable(false);
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void loadOrderIds() throws SQLException {
        ArrayList<String> orderIds = returnBO.getAllOrderIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(orderIds);
        comOrderID.setItems(observableList);
    }
    private void refreshTable() throws Exception {
        ArrayList<Return> returnDTOS = returnBO.getAllReturns();

        ObservableList<ReturnTM> returnS = FXCollections.observableArrayList();

        for(Return returnDTO : returnDTOS){
            ReturnTM returnTM = new ReturnTM(
                    returnDTO.getReturnID(),
                    returnDTO.getItemName(),
                    returnDTO.getItemPrice(),
                    returnDTO.getReason(),
                    returnDTO.getDate(),
                    returnDTO.getOrderID()
            );
            returnS.add(returnTM);
        }
        tblReturn.setItems(returnS);
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) throws Exception {
        String returnId = lblReturnID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Delete This Return?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = returnBO.deleteReturn(returnId);

            if (isDeleted) {
                lblNotify.setText("Return Delete Successfully");
                new Alert(Alert.AlertType.INFORMATION, "Return Deleted...!").show();
                refreshPage();
            } else {
                lblNotify.setText("Return Delete Unsuccessfully");
                new Alert(Alert.AlertType.ERROR, "Fail To Delete Return...!").show();
            }
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws Exception {
        refreshPage();

    }

    @FXML
    void btnReturnReportOnAction(ActionEvent event) {
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws Exception {

        String returnId = lblReturnID.getText();
        String itemName = txtItemName.getText();
        String itemPriceString = txtItemPrice.getText();
        String reason = txtReason.getText();
        String date = txtDate.getText();
        String orderID = comOrderID.getSelectionModel().getSelectedItem();

        String itemPricePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidItemName = itemName != null;
        boolean isValidItemPrice = itemPriceString.matches(itemPricePattern);
        boolean isValidReason = reason != null;
        boolean isValidDate = date != null;
        boolean isValidOrderId = orderID != null;


        if (!isValidItemName) {
            txtItemName.setStyle(txtItemName.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidItemPrice) {
            txtItemPrice.setStyle(txtItemPrice.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidReason) {
            txtReason.setStyle(txtReason.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidDate) {
            txtDate.setStyle(txtDate.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidOrderId) {
            comOrderID.setStyle(comOrderID.getStyle() + "; -fx-border-color: red;");
        }

        if (isValidItemName && isValidItemPrice && isValidReason && isValidDate && isValidOrderId) {
            BigDecimal itemPrice = new BigDecimal(itemPriceString);

            ReturnDTO returnDTO = new ReturnDTO(returnId, itemName, itemPrice, reason, date, orderID);

            boolean isSaved = returnBO.saveReturn(returnDTO);

            if (isSaved) {
                lblNotify.setText("Return Saved Successfully");
                new Alert(Alert.AlertType.INFORMATION, "Return Saved...!").show();
                refreshPage();
            } else {
                lblNotify.setText("Return Save Unsuccessfully");
                new Alert(Alert.AlertType.ERROR, "Fail To Save Return...!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws Exception {
        String returnId = lblReturnID.getText();
        String itemName = txtItemName.getText();
        String itemPriceString = txtItemPrice.getText();
        String reason = txtReason.getText();
        String date = txtDate.getText();
        String orderID = comOrderID.getSelectionModel().getSelectedItem();

        String itemPricePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidItemName = itemName != null;
        boolean isValidItemPrice = itemPriceString.matches(itemPricePattern);
        boolean isValidReason = reason != null;
        boolean isValidDate = date != null;
        boolean isValidOrderId = orderID != null;

        if (!isValidItemName) {
            txtItemName.setStyle(txtItemName.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidItemPrice) {
            txtItemPrice.setStyle(txtItemPrice.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidReason) {
            txtReason.setStyle(txtReason.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidDate) {
            txtDate.setStyle(txtDate.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidOrderId) {
            comOrderID.setStyle(comOrderID.getStyle() + "; -fx-border-color: red;");
        }

        if (isValidItemName && isValidItemPrice && isValidReason && isValidDate && isValidOrderId) {
            BigDecimal itemPrice = new BigDecimal(itemPriceString);

            ReturnDTO returnDTO = new ReturnDTO(returnId, itemName, itemPrice, reason, date, orderID);

            boolean isUpdated = returnBO.updateReturn(returnDTO);

            if (isUpdated) {
                lblNotify.setText("Return Updated Successfully");
                new Alert(Alert.AlertType.INFORMATION, "Return Updated...!").show();
                refreshPage();
            } else {
                lblNotify.setText("Return Update Unsuccessfully");
                new Alert(Alert.AlertType.ERROR, "Fail To Update Return...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        ReturnTM selectedReturn = tblReturn.getSelectionModel().getSelectedItem();

        if (selectedReturn != null) {
            lblReturnID.setText(selectedReturn.getReturnID());
            comOrderID.setValue(selectedReturn.getOrderID());
            txtDate.setText(selectedReturn.getDate());
            txtItemName.setText(selectedReturn.getItemName());
            txtItemPrice.setText(selectedReturn.getItemPrice().toString());
            txtReason.setText(selectedReturn.getReason());

            btnReset.setDisable(false);
            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnReturnReport.setDisable(false);
        }
    }
}
