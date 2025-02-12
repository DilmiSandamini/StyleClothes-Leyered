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
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.CategoryBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl.CategoryDAOImpl;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.CategoryDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Category;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.view.tdm.CategoryTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CategoryController implements Initializable {

    @FXML
    private AnchorPane anchorPaneCategory;

    @FXML
    private Button btnCategoryReport;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<CategoryTM, String> colCategoryID;

    @FXML
    private TableColumn<CategoryTM , String> colCategoryName;

    @FXML
    private TableColumn<CategoryTM , String> colDescription;

    @FXML
    private Label lblCategoryID;

    @FXML
    private Label lblNotify;

    @FXML
    private TableView<CategoryTM> tblCategory;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtName;

    CategoryBO categoryBO =(CategoryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CATEGORY);
   // final CategoryDAOImpl categoryDAO = new CategoryDAOImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCategoryID.setCellValueFactory(new PropertyValueFactory<>("categoryID"));
        colCategoryName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextCategoryID = categoryBO.getNextCategoryId();
        lblCategoryID.setText(nextCategoryID);

        txtName.setText("");
        txtDescription.setText("");

        btnSave.setDisable(false);

        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
        btnCategoryReport.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        tblCategory.getItems().clear();
        try {
            ArrayList<Category> allCategory = categoryBO.getAllCategoryS();
            ObservableList<CategoryTM> categoryTMS = FXCollections.observableArrayList();

            for (Category ca : allCategory) {
                categoryTMS.add(new CategoryTM(
                        ca.getCategoryID(),
                        ca.getCategoryName(),
                        ca.getDescription()
                ));
            }
            tblCategory.setItems(categoryTMS);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnCategoryReportOnAction(ActionEvent event) throws SQLException {
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String categoryId = lblCategoryID.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this category?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = categoryBO.deleteCategory(categoryId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Category Delete...!").show();
                lblNotify.setText("Category Successfully Delete!");
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail To Delete Category...!").show();
                lblNotify.setText("Failed To Delete Category.");
            }
        }

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String categoryId = lblCategoryID.getText();
        String categoryName = txtName.getText();
        String description = txtDescription.getText();

        String namePattern = "^[A-Za-z ]+$";

        boolean isValidName = categoryName.matches(namePattern);

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: #7367F0;");


        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }
        if(!isValidName){
            txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName ) {
            CategoryDTO customerDTO = new CategoryDTO(categoryId , categoryName , description);

            boolean isSaved = categoryBO.saveCategory(customerDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Category Saved...!").show();
                lblNotify.setText("Category Successfully Saved!");
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail To Save Category...!").show();
                lblNotify.setText("Failed To Save Category.");
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        String categoryId = lblCategoryID.getText();
        String categoryName = txtName.getText();
        String description = txtDescription.getText();


        String namePattern = "^[A-Za-z ]+$";
        String nicPattern = "^[0-9]{9}[vVxX]||[0-9]{12}$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String phonePattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";


        boolean isValidName = categoryName.matches(namePattern);


        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: #7367F0;");

        if (!isValidName) {
            txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: red;");
        }


        if (isValidName) {

            CategoryDTO categoryDTO = new CategoryDTO(categoryId, categoryName, description);

            boolean isUpdate = categoryBO.updateCategory(categoryDTO);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Category Update...!").show();
                lblNotify.setText("Category Successfully Update!");
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail To Update Category...!").show();
                lblNotify.setText("Failed To Update category.");
            }
        }
    }
    @FXML
    void onClickTable(MouseEvent event) {
        CategoryTM selectedItem = tblCategory.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblCategoryID.setText(selectedItem.getCategoryID());
            txtName.setText(selectedItem.getCategoryName());
            txtDescription.setText(selectedItem.getDescription());

            btnSave.setDisable(true);

            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }
}

