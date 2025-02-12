package lk.ijse.gdse72.styleclothesleyeredarchitecture.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane anchorPaneDashboard;

    @FXML
    private JFXButton btnCategory;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnItem;

    @FXML
    private JFXButton btnLogOut;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private JFXButton btnPayment;

    @FXML
    private JFXButton btnReturn;

    @FXML
    private JFXButton btnSupplier;

    @FXML
    private AnchorPane dashBoardLoadingTablePage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void NavigateToCategory(ActionEvent event) {
        navigateTo("/view/CategoryForm.fxml");
    }

    @FXML
    void NavigateToCustomerPage(ActionEvent event) {
        navigateTo("/view/CustomerForm.fxml");
    }
    @FXML
    void NavigateToEmployeePage(ActionEvent event) {
        navigateTo("/view/EmployeeForm.fxml");
    }

    @FXML
    void NavigateToItemPage(ActionEvent event) {
        navigateTo("/view/ItemForm.fxml");
    }

    @FXML
    void NavigateToOrderPage(ActionEvent event) {
        navigateTo("/view/OrderForm.fxml");
    }

    @FXML
    void NavigateToPaymentPage(ActionEvent event) {
        navigateTo("/view/PaymentForm.fxml");
    }

    @FXML
    void NavigateToReturnPage(ActionEvent event) {
        navigateTo("/view/ReturnForm.fxml");
    }

    @FXML
    void NavigateToSupplierPage(ActionEvent event) {
        navigateTo("/view/SupplierForm.fxml");
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        anchorPaneDashboard.getChildren().clear();
        AnchorPane load = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        anchorPaneDashboard.getChildren().add(load);
    }

    private void navigateTo(String fxmlPath) {
        try {
            dashBoardLoadingTablePage.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));
            load.prefWidthProperty().bind(dashBoardLoadingTablePage.widthProperty());
            load.prefHeightProperty().bind(dashBoardLoadingTablePage.heightProperty());
            dashBoardLoadingTablePage.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }
}
