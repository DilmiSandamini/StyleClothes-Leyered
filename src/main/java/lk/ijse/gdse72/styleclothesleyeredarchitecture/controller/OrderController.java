package lk.ijse.gdse72.styleclothesleyeredarchitecture.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.BOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.PurchaseOrderBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.CustomerDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.ItemDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.OrderDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.OrderDetailsDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Customer;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Item;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.OrderDetails;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.view.tdm.CartTM;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderController implements Initializable {

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnPay;

    @FXML
    private Button btnReset;

    @FXML
    private ComboBox<String> cmbCustomerId;

    @FXML
    private ComboBox<String> cmbItemId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<CartTM , String> colItemId;

    @FXML
    private TableColumn<CartTM , String > colName;

    @FXML
    private TableColumn<CartTM , BigDecimal> colPrice;

    @FXML
    private TableColumn<CartTM , Integer> colQuantity;

    @FXML
    private TableColumn<CartTM , BigDecimal> colTotal;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblItemPrice;

    @FXML
    private Label lblItemQty;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label orderDate;

    @FXML
    private TableView<CartTM> tblCart;

    @FXML
    private JFXTextField txtAddToCartQty;

    PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PURCHASE_ORDER);
    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValues();

        try {
            refreshPage();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }
    }

    private void refreshPage() throws SQLException {
        lblOrderId.setText(purchaseOrderBO.getNextOrderId());

        orderDate.setText(LocalDate.now().toString());

        loadCustomerIds();
        loadItemId();

        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemId.getSelectionModel().clearSelection();
        lblItemName.setText("");
        lblItemQty.setText("");
        lblItemPrice.setText("");
        txtAddToCartQty.setText("");
        lblCustomerName.setText("");

        cartTMS.clear();

        tblCart.refresh();
    }

    private void loadItemId() throws SQLException {
        ArrayList<String> itemIds = purchaseOrderBO.getAllItemIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(itemIds);
        cmbItemId.setItems(observableList);
    }

    private void loadCustomerIds() throws SQLException {
        ArrayList<String> customerIds = purchaseOrderBO.getAllCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        cmbCustomerId.setItems(observableList);
    }

    private void setCellValues() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("cartQuantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("removeBtn"));

        tblCart.setItems(cartTMS);
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String selectedItemId = cmbItemId.getValue();

        if (selectedItemId == null) {
            new Alert(Alert.AlertType.ERROR, "Please select item..!").show();
            return;
        }

        String quantityPattern = "^[0-9]+$";

        boolean isValidQty = txtAddToCartQty.getText().matches(quantityPattern);

        if (!isValidQty) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            return;
        }

        String itemName = lblItemName.getText();
        int cartQty = Integer.parseInt(txtAddToCartQty.getText());
        int qtyOnHand = Integer.parseInt(lblItemQty.getText());

        if (qtyOnHand < cartQty) {
            new Alert(Alert.AlertType.ERROR, "Not enough items..!").show();
            return;
        }

        txtAddToCartQty.setText("");

        double unitPrice = Double.parseDouble(lblItemPrice.getText());
        double total = unitPrice * cartQty;

        for (CartTM cartTM : cartTMS) {

            if (cartTM.getItemId().equals(selectedItemId)) {
                int newQty = cartTM.getCartQuantity() + cartQty;
                cartTM.setCartQuantity(newQty);
                cartTM.setTotal(unitPrice * newQty);


                tblCart.refresh();
                return;
            }
        }


        Button btn = new Button("Remove");

        CartTM newCartTM = new CartTM(
                selectedItemId,
                itemName,
                cartQty,
                unitPrice,
                total,
                btn
        );

        btn.setOnAction(actionEvent -> {

            cartTMS.remove(newCartTM);

            tblCart.refresh();
        });

        cartTMS.add(newCartTM);
    }

    @FXML
    void btnPayOnAction(ActionEvent event) throws SQLException {
        if (tblCart.getItems().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please add items to cart..!").show();
            return;
        }
        if (cmbCustomerId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select customer for place order..!").show();
            return;
        }

        String orderId = lblOrderId.getText();
        Date dateOfOrder = Date.valueOf(orderDate.getText());
        String customerId = cmbCustomerId.getValue();

        ArrayList<OrderDetails> orderDetailsDTOS = new ArrayList<>();

        for (CartTM cartTM : cartTMS) {

            OrderDetails orderDetails = new OrderDetails(
                    orderId,
                    cartTM.getItemId(),
                    cartTM.getCartQuantity(),
                    cartTM.getUnitPrice()
            );

            orderDetailsDTOS.add(orderDetails);
        }

        OrderDTO orderDTO = new OrderDTO(
                orderId,
                customerId,
                dateOfOrder,
                orderDetailsDTOS
        );

        boolean isSaved = purchaseOrderBO.saveOrder(orderDTO);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order saved..!").show();

            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order fail..!").show();
        }
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException {
        String selectedCustomerId = cmbCustomerId.getSelectionModel().getSelectedItem();
        CustomerDTO customer = purchaseOrderBO.findByCustomerId(selectedCustomerId);

        if (customer != null) {

            lblCustomerName.setText(customer.getName());
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) throws SQLException {
        String selectedItemId = cmbItemId.getSelectionModel().getSelectedItem();
        ItemDTO item = purchaseOrderBO.findByItemId(selectedItemId);

        if (item != null) {

            lblItemName.setText(item.getItemName());
            lblItemQty.setText(String.valueOf(item.getQuantity()));
            lblItemPrice.setText(String.valueOf(item.getPrice()));
        }
    }
}

