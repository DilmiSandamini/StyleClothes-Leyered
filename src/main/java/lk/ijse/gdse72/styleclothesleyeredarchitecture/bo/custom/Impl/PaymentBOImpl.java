package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.Impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.BOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.CustomerBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.PaymentBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.PaymentDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.PaymentDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public String getNextPaymentId() throws Exception {
        return paymentDAO.getNextId();
    }

    @Override
    public ArrayList<PaymentDTO> getAllPayments() throws SQLException {
        ArrayList<Payment> allEntityData = paymentDAO.getAll();
        ArrayList<PaymentDTO> allDTOData = new ArrayList<>();
        for (Payment p : allEntityData) {
            allDTOData.add(new PaymentDTO(
                    p.getPaymentID(),
                    p.getAmount(),
                    p.getPaymentMethod(),
                    p.getDate(),
                    p.getCustomerID()
            ));
        }
        return allDTOData;
    }

    @Override
    public boolean deletePayment(String paymentID) throws SQLException {
        return paymentDAO.delete(paymentID);
    }

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException {
        return paymentDAO.save(new Payment(
                paymentDTO.getPaymentID(),
                paymentDTO.getAmount(),
                paymentDTO.getPaymentMethod(),
                paymentDTO.getDate(),
                paymentDTO.getCustomerID()
        ));
    }

    @Override
    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException {
        return paymentDAO.update(new Payment(
                paymentDTO.getPaymentID(),
                paymentDTO.getAmount(),
                paymentDTO.getPaymentMethod(),
                paymentDTO.getDate(),
                paymentDTO.getCustomerID()
        ));
    }

    @Override
    public String getCustomerNameById(String customerId) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<String> getAllCustomerIds() throws SQLException {
        return null;
    }
}
