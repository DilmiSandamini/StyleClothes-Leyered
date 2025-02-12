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
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.ItemBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.CategoryDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl.CategoryDAOImpl;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.ItemDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.view.tdm.ItemTM;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    @FXML
    private AnchorPane anchorPaneItem;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnItemReport;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<ItemTM, String> colCategoryID;

    @FXML
    private TableColumn<ItemTM, String> colItemID;

    @FXML
    private TableColumn<ItemTM, String> colItemName;

    @FXML
    private TableColumn<ItemTM, BigDecimal> colItemPrice;

    @FXML
    private TableColumn<ItemTM, Integer> colQuantity;

    @FXML
    private JFXComboBox<String> comCategoryID;

    @FXML
    private Label lblItemID;

    @FXML
    private Label lblNotify;

    @FXML
    private Label lblShowCategory;

    @FXML
    private TableView<ItemTM> tblItem;

    @FXML
    private JFXTextField txtItemName;

    @FXML
    private JFXTextField txtItemPrice;

    @FXML
    private JFXTextField txtQuantity;

    ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    private final CategoryDAO categoryDAO = new CategoryDAOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCategoryID.setCellValueFactory(new PropertyValueFactory<>("categoryID"));

        try {
            refreshPage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        comCategoryID.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            try {
                if (newValue != null) {
                    lblShowCategory.setText(categoryDAO.getCategoryNameById(newValue));
                } else{
                    lblShowCategory.setText("");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }


    private void refreshPage() throws SQLException {
        refreshTable();

        String nextItemId = itemBO.getNextItemId();
        lblItemID.setText(nextItemId);

        txtItemName.setText("");
        txtItemPrice.setText("");
        txtQuantity.setText("");

        loadCategoryIds();
        comCategoryID.getSelectionModel().clearSelection();


        btnReset.setDisable(false);
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void loadCategoryIds() throws SQLException {
        ArrayList<String> categoryIds = categoryDAO.getAllCategoryIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(categoryIds);
        comCategoryID.setItems(observableList);
    }

    private void refreshTable() throws SQLException {
        ArrayList<ItemDTO> itemDTOS = itemBO.getAllItems();
        ObservableList<ItemTM> itemTMS = FXCollections.observableArrayList();

        for (ItemDTO itemDTO : itemDTOS) {
            ItemTM itemTM = new ItemTM(
                    itemDTO.getItemID(),
                    itemDTO.getItemName(),
                    itemDTO.getPrice(),
                    itemDTO.getQuantity(),
                    itemDTO.getCategoryID()
            );
            itemTMS.add(itemTM);
        }
        tblItem.setItems(itemTMS);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String itemId = lblItemID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure You Want To Delete This Item?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = itemBO.deleteItem(itemId);

            if (isDeleted) {
                lblNotify.setText("Item Delete Successfully");
                new Alert(Alert.AlertType.INFORMATION, "Item Deleted...!").show();
                refreshPage();
            } else {
                lblNotify.setText("Item Delete Unsuccessfully");
                new Alert(Alert.AlertType.ERROR, "Fail To Delete Item...!").show();
            }
        }

    }

    @FXML
    void btnItemReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String itemID = lblItemID.getText();
        String itemName = txtItemName.getText();
        String price = txtItemPrice.getText();
        String quantity = txtQuantity.getText();
        String categoryID = comCategoryID.getSelectionModel().getSelectedItem();

        String pricePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";
        String quantityPattern = "^[0-9]+$";

        boolean isValidName = itemName != null;
        boolean isValidPrice = price.matches(pricePattern);
        boolean isValidQuantity = quantity.matches(quantityPattern);

        if (!isValidName) {
            txtItemName.setStyle(txtItemName.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidPrice) {
            txtItemPrice.setStyle(txtItemPrice.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidQuantity) {
            txtQuantity.setStyle(txtQuantity.getStyle() + "; -fx-border-color: red;");
        }

        if (isValidName && isValidPrice && isValidQuantity) {
            BigDecimal price1 = new BigDecimal(price);
            ItemDTO itemDTO = new ItemDTO(itemID, itemName, new BigDecimal(price), Integer.parseInt(quantity), categoryID);
            boolean isSaved = itemBO.saveItem(itemDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Item Save...!").show();
                lblNotify.setText("Item Successfully Saved!");
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail To Save Item...!").show();
                lblNotify.setText("Failed To Save Item.");
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String itemID = lblItemID.getText();
        String itemName = txtItemName.getText();
        String price = txtItemPrice.getText();
        String quantity = txtQuantity.getText();
        String categoryID = comCategoryID.getSelectionModel().getSelectedItem();

        String quantityPattern = "^[0-9]+$";
        String pricePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = itemName != null;
        boolean isValidPrice = price.matches(pricePattern);
        boolean isValidQuantity = quantity.matches(quantityPattern);

        if (!isValidName) {
            txtItemName.setStyle(txtItemName.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidPrice) {
            txtItemPrice.setStyle(txtItemPrice.getStyle() + "; -fx-border-color: red;");
        }

        if (!isValidQuantity) {
            txtQuantity.setStyle(txtQuantity.getStyle() + "; -fx-border-color: red;");
        }

        if (isValidName && isValidPrice && isValidQuantity) {
            BigDecimal price1 = new BigDecimal(price);
            ItemDTO itemDTO = new ItemDTO(itemID, itemName, new BigDecimal(price), Integer.parseInt(quantity), categoryID);
            boolean isUpdated = itemBO.updateItem(itemDTO);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Item Update...!").show();
                lblNotify.setText("Item Successfully Updated!");
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail To Update Item...!").show();
                lblNotify.setText("Failed To Update Item.");
            }

        }
    }

    public void onClickTable(javafx.scene.input.MouseEvent mouseEvent) {
        ItemTM selectedItem = tblItem.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblItemID.setText(selectedItem.getItemID());
            txtItemName.setText(selectedItem.getItemName());
            txtItemPrice.setText(selectedItem.getPrice().toString());
            txtQuantity.setText(String.valueOf(selectedItem.getQuantity()));
            comCategoryID.getSelectionModel().select(selectedItem.getCategoryID());

            btnReset.setDisable(false);
            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
            btnItemReport.setDisable(false);
        }

    }
}