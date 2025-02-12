package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.Impl;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom.ReturnBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.DAOFactory;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.SQLUtil;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dao.custom.ReturnDAO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.ReturnDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Return;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnBOImpl implements ReturnBO {

    ReturnDAO returnDAO = (ReturnDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RETURN);

    @Override
    public String getNextReturnId() throws SQLException{
        return returnDAO.getNextId();
    }

    @Override
    public ArrayList<String> getAllOrderIds() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT orderId FROM orders");

        ArrayList<String> orderIds = new ArrayList<>();

        while (rst.next()) {
            orderIds.add(rst.getString(1));
        }

        return orderIds;
    }

    @Override
    public ArrayList<Return> getAllReturns() throws Exception {
        ArrayList<ReturnDTO> returnDTOS = new ArrayList<>();
        ArrayList<Return> allReturns = returnDAO.getAll();

        for (Return r : allReturns) {
            returnDTOS.add(new ReturnDTO(
                    r.getReturnID(),
                    r.getItemName(),
                    r.getItemPrice(),
                    r.getReason(),
                    r.getDate(),
                    r.getOrderID()
            ));
        }
        return allReturns;
    }

    @Override
    public boolean deleteReturn(String returnID) throws Exception {
        return returnDAO.delete(returnID);
    }

    @Override
    public boolean saveReturn(ReturnDTO returnDTO) throws Exception {
        return returnDAO.save(new Return(
                returnDTO.getReturnID(),
                returnDTO.getItemName(),
                returnDTO.getItemPrice(),
                returnDTO.getReason(),
                returnDTO.getDate(),
                returnDTO.getOrderID()
        ));
    }

    @Override
    public boolean updateReturn(ReturnDTO returnDTO) throws Exception {
        return returnDAO.update(new Return(
                returnDTO.getReturnID(),
                returnDTO.getItemName(),
                returnDTO.getItemPrice(),
                returnDTO.getReason(),
                returnDTO.getDate(),
                returnDTO.getOrderID()
        ));
    }
}
