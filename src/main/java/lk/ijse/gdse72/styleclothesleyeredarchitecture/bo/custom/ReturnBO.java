package lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.custom;

import lk.ijse.gdse72.styleclothesleyeredarchitecture.bo.SuperBO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.dto.ReturnDTO;
import lk.ijse.gdse72.styleclothesleyeredarchitecture.entity.Return;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReturnBO extends SuperBO {
    String getNextReturnId() throws Exception;
    ArrayList<String> getAllOrderIds() throws SQLException;
    ArrayList<Return> getAllReturns() throws Exception;
    boolean deleteReturn(String returnID) throws Exception;
    boolean saveReturn(ReturnDTO returnDTO) throws Exception;
    boolean updateReturn(ReturnDTO returnDTO) throws Exception;
}
