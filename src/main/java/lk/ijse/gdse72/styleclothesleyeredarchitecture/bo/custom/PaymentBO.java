package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.SuperBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
    String getCustomerNameById(String customerId) throws SQLException;
    ArrayList<String> getAllCustomerIds() throws SQLException;
    String getNextPaymentId() throws Exception;
    ArrayList<PaymentDTO> getAllPayments() throws SQLException;
    boolean deletePayment(String paymentID) throws SQLException;
    boolean savePayment(PaymentDTO paymentDTO) throws SQLException;
    boolean updatePayment(PaymentDTO paymentDTO) throws SQLException;
}
