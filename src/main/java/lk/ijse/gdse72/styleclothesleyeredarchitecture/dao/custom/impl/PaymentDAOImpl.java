package lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.SQLUtil;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.PaymentDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.PaymentDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public String getNextId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT paymentID FROM payment ORDER BY paymentID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }
    @Override
    public ArrayList<Payment> getAll() throws SQLException {
        ResultSet rst = SQLUtil.execute("select * from payment");

        ArrayList<Payment> paymentS = new ArrayList<>();

        while (rst.next()) {
            Payment payment = new Payment(
                    rst.getString(1),
                    rst.getBigDecimal(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
            paymentS.add(payment);
        }
        return paymentS;
    }

    @Override
    public boolean save(Payment entity) throws SQLException {
        return SQLUtil.execute(
                "insert into payment values (?,?,?,?,?)",
                entity.getPaymentID(),
                entity.getAmount(),
                entity.getPaymentMethod(),
                entity.getDate(),
                entity.getCustomerID()
        );
    }

    @Override
    public boolean update(Payment entity) throws SQLException {
        return SQLUtil.execute(
                "update payment set amount=?, paymentMethod=?, date=?,customerID=? where paymentID=?",
                entity.getAmount(),
                entity.getPaymentMethod(),
                entity.getDate(),
                entity.getCustomerID(),
                entity.getPaymentID()
        );
    }

    @Override
    public boolean delete(String paymentID) throws SQLException {
        return SQLUtil.execute("delete from payment where paymentID=?", paymentID);
    }
}
